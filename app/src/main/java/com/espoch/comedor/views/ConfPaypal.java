package com.espoch.comedor.views;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espoch.comedor.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class ConfPaypal extends AppCompatActivity {

    public static final String PAYPAL_CLIENT_ID = "AYmapNEpSRLSIYgU53JEyViEEcOiLMv-h8ZItEsRvjxSCpPsa7BZQtMCDD8bmvZyT6eiPqSIXGDofcwg";
    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static final int MY_CAMERA_PERMISSION_CODE = 1001;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Cambia a ENVIRONMENT_PRODUCTION para producción
            .clientId(PAYPAL_CLIENT_ID);

    Button btnPagar;
    EditText btnamount;
    String monto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conf_paypal);

        // Iniciar Servicio Paypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        btnPagar = findViewById(R.id.btnPagar);
        btnamount = findViewById(R.id.btnamount);

        btnPagar.setOnClickListener(v -> procesarPago());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        crearCanalNotificacion("Pago Exitoso", "Canal de Pago");
    }

    private void capturarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            // Lógica para capturar la foto
        }
    }

    private void procesarPago() {
        monto = btnamount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(monto), "USD", "Pagador por USUARIO", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private void crearCanalNotificacion(String canalId, String canalNombre) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canal = new NotificationChannel(canalId, canalNombre, importancia);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal);
        }
    }

    private void mostrarNotificacion(String titulo, String contenido) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Pago Exitoso")
                .setSmallIcon(R.drawable.ic_polidish_24dp)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, DetallesPago.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", monto));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al procesar la confirmación de pago", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Pago cancelado", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Configuración de pago inválida", Toast.LENGTH_SHORT).show();
            } else {
                if (data != null && data.hasExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)) {
                    try {
                        PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            JSONObject jsonResponse = new JSONObject(confirmation.toJSONObject().toString(4));
                            String state = jsonResponse.getJSONObject("response").getString("state");
                            if ("approved".equals(state)) {
                                Toast.makeText(this, "Pago aprobado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Error en el pago", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
