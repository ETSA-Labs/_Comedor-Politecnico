package com.espoch.comedor.views;

import android.Manifest;
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
    EditText edtAmount;
    Button btnPayment;
    String clientId = "ATH-G_NxhaoYw-GPqlseviV4QaISYZLfQcsp7FbkKDIkoQmQSn23KJGyw--G5e9W8jXR3zw2VtMi55kM";

    int PAYPAL_REQUEST_CODE = 123;

    public static PayPalConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conf_paypal);
        //Inicializar Paypal
        edtAmount = findViewById(R.id.edtamount);
        btnPayment = findViewById(R.id.btnpayment);
        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);


        //onfiguracion de Boton de Pago
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Canal de Notificacion
        crearCanalNotificacion("Pago Exitoso", "Canal de Pago");

    }

    private void getPayment() {
        String amounts = edtAmount.getText().toString();
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amounts)), "USD", "Code with Arvind", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PAYPAL_REQUEST_CODE) {

            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (paymentConfirmation != null) {


                try {
                    String paymentDetails = paymentConfirmation.toJSONObject().toString();

                    JSONObject object = new JSONObject(paymentDetails);

                } catch (JSONException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == PaymentActivity.RESULT_CANCELED) {

                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

            }

        } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

            Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();

        }
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
}