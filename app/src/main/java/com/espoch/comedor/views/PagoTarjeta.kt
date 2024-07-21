package com.espoch.comedor.views

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.espoch.comedor.R

class PagoTarjeta : AppCompatActivity() {
    private lateinit var btnPagar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_tarjeta)
        btnPagar = findViewById(R.id.buttonPago)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //Llamado Push Notificacion
        btnPagar.setOnClickListener {
            Log.d("PagoTarjeta", "Botón pagar clicado")
            crearCanalNotificacion("Pago Correcto", "Canal de Pago")
            crearNotificacion("Pago Correcto", "Su pago ha sido realizado correctamente.", 2)
            // Notificación de 30 minutos para retirar comida
            Handler(Looper.getMainLooper()).postDelayed({
                Log.d("PagoTarjeta", "30 minutos transcurridos, mostrando notificación de retiro")
                crearCanalNotificacion("Aviso de Retiro", "Canal de Aviso")
                crearNotificacion("Aviso", "Tiene 30 minutos para retirar su comida.", 3)
            }, 1800000) // 30 minutos en milisegundos
        }
    }
    private fun crearCanalNotificacion(canalId: String, canalNombre: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canalImportancia = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel(canalId, canalNombre, canalImportancia)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }
    private fun crearNotificacion(titulo: String, texto: String, idNotificacion: Int) {
        val builder = NotificationCompat.Builder(this, "Pago Correcto")
            .setSmallIcon(R.drawable.ic_polidish_24dp)
            .setContentTitle(titulo)
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(this@PagoTarjeta, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Log.d("PagoTarjeta", "Permiso de notificaciones no concedido")
                return
            }
            notify(idNotificacion, builder.build())
        }
    }
}