package com.espoch.comedor.services;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espoch.comedor.R;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Stripe extends AppCompatActivity {
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    double baseAmount = 150.0; // Monto base en centavos (150.0 centavos = 1.50 USD)
    int quantity = 1; // Cantidad inicial
    PaymentSheet.CustomerConfiguration customerConfig;
    Button btnConfirm;
    ImageButton btnMas, btnMenos;
    TextView etPrice, tvCantidad, tvDate, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_reservation);

        // Inicializar las vistas
        btnConfirm = findViewById(R.id.btnConfirm);
        etPrice = findViewById(R.id.etPrice);
        tvCantidad = findViewById(R.id.tvcantidad);
        btnMas = findViewById(R.id.btnmas);
        btnMenos = findViewById(R.id.btnmenos);
        tvDate = findViewById(R.id.etDate); // Asumiendo que `etDate` es un `TextView` ahora
        tvTime = findViewById(R.id.etTime); // Asumiendo que `etTime` es un `TextView` ahora

        // Configurar PaymentSheet
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        // Configurar los listeners de los botones
        btnConfirm.setOnClickListener(view -> getDetails());
        btnMas.setOnClickListener(view -> updateQuantity(1));
        btnMenos.setOnClickListener(view -> updateQuantity(-1));

        // Configurar la vista para manejar los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mostrar precio inicial
        updatePrice();

        // Establecer la fecha y hora actuales
        setCurrentDateTime();
    }

    private void setCurrentDateTime() {
        // Obtener la fecha y hora actuales
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String currentDate = dateFormat.format(calendar.getTime());
        String currentTime = timeFormat.format(calendar.getTime());

        // Establecer los valores en los TextViews
        tvDate.setText(currentDate);
        tvTime.setText(currentTime);
    }

    void getDetails() {
        Fuel.INSTANCE.post("https://helloworld-sxvbtspwuq-uc.a.run.app/helloworld?amt=" + baseAmount * quantity, null).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                try {
                    JSONObject result = new JSONObject(s);
                    customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getString("customer"),
                            result.getString("ephemeralKey")
                    );
                    paymentIntentClientSecret = result.getString("paymentIntent");
                    PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                    // Actualizar el TextView con el precio
                    runOnUiThread(() -> {
                        updatePrice();
                        showStripePaymentSheet();
                    });

                } catch (JSONException e) {
                    Log.e("Stripe", "Error al parsear la respuesta JSON", e);
                    Toast.makeText(Stripe.this, "Error al obtener el precio. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(@NonNull FuelError fuelError) {
                Log.e("Stripe", "Error en la solicitud al servidor", fuelError);
                Toast.makeText(Stripe.this, "Error al obtener el precio. Intente nuevamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updateQuantity(int change) {
        quantity += change;
        if (quantity < 1) {
            quantity = 1;
        } else if (quantity > 2) {
            quantity = 2;
        }
        tvCantidad.setText(String.valueOf(quantity));
        updatePrice();
    }

    void updatePrice() {
        double amountDouble = baseAmount * quantity;
        etPrice.setText(String.format("$%.2f", amountDouble / 100.0));
    }

    void showStripePaymentSheet() {
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Example, Inc.")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentIntentClientSecret,
                configuration
        );
    }

    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Pago cancelado", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().toString(), Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            sendNotification("Pago Realizado", "Su reserva se realizó exitosamente. Le recordamos que tiene 30 minutos para retirar su comida");
        }
    }

    private void sendNotification(String title, String message) {
        String CHANNEL_ID = "Pago Correcto"; // Debe coincidir con el canal creado
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_polidish_24dp) // Reemplaza con tu ícono
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true); // Opcional: para que la notificación se elimine cuando se toque

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permisos si es necesario
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
