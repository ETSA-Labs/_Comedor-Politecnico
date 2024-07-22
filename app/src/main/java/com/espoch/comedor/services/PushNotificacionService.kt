package com.espoch.comedor.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service.START_NOT_STICKY
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.espoch.comedor.MainActivity
import com.espoch.comedor.R
import com.espoch.comedor.ReservationConfirmationActivity
import com.espoch.comedor.views.PagoTarjeta
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import com.onesignal.notifications.IPermissionObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushNotificacionService : AppCompatActivity() {

    private val ONESIGNAL_APP_ID = "86ae695b-f765-4e4c-989c-7875ba0def33"
    private val canalReserva = "Reserva Exitosa"
    private val canalPago = "Pago Correcto"
    private val canalAviso = "Aviso de Retiro"
    private val canalID = "canalId"
    private val notificacionIdReserva = 1
    private val notificacionIdPago = 2
    private val notificacionIdAviso = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.addPermissionObserver(
                object : IPermissionObserver {
                    override fun onNotificationPermissionChange(permission: Boolean) {
                        this@PushNotificacionService.finish()
                    }
                }
            )

            if (!OneSignal.Notifications.requestPermission(false))
                this@PushNotificacionService.finish()
        }
        enableEdgeToEdge()

        // Notificaciones Push Manuales
        setupNotificationChannels()

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido concedido
                Log.d("PushNotificacionService", "Permiso de notificaciones concedido")
            } else {
                // El permiso ha sido denegado
                Log.d("PushNotificacionService", "Permiso de notificaciones denegado")
            }
        }
    }

    private fun setupNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            crearCanalNotificacion(canalReserva, "Reserva Exitosa")
            crearCanalNotificacion(canalPago, "Pago Correcto")
            crearCanalNotificacion(canalAviso, "Aviso de Retiro")
        }
    }

    private fun crearCanalNotificacion(canalId: String, canalNombre: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canalImportancia = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel(canalId, canalNombre, canalImportancia)
            canal.description = "Descripción del canal $canalNombre"
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }

    private fun crearNotificacion(title: String, text: String, notificacionId: Int) {
        val canalId = when (notificacionId) {
            notificacionIdReserva -> canalReserva
            notificacionIdPago -> canalPago
            notificacionIdAviso -> canalAviso
            else -> canalReserva
        }

        val resultIntent = when (notificacionId) {
            notificacionIdReserva -> Intent(this, ReservationConfirmationActivity::class.java)
            notificacionIdPago -> Intent(this, PagoTarjeta::class.java)
            notificacionIdAviso -> Intent(this, MainActivity::class.java)
            else -> Intent(this, MainActivity::class.java)
        }
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val resultPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificacion = NotificationCompat.Builder(this, canalId).apply {
            setContentTitle(title)
            setContentText(text)
            setSmallIcon(R.drawable.ic_polidish_24dp)
            priority = NotificationCompat.PRIORITY_HIGH
            setContentIntent(resultPendingIntent)
            setAutoCancel(true)
        }.build()

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PushNotificacionService", "Permiso de notificaciones no concedido")
            return
        }
        notificationManager.notify(notificacionId, notificacion)
        Log.d("PushNotificacionService", "Notificación enviada con ID: $notificacionId")
    }

}
