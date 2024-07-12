package com.espoch.comedor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class SimulacionNotificacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulacion_notificaciones)

        // Simula la notificación de reserva exitosa
        simulateReservationSuccessNotification()

        // Simula la notificación de pago realizado
        simulatePaymentConfirmationNotification()

        // Simula la notificación de recordatorio de retiro
        simulatePickupReminderNotification()
    }

    private fun simulateReservationSuccessNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val reservationId = "12345" // Simula un ID de reserva
            val notificationJson = """
                {
                    "app_id": "86ae695b-f765-4e4c-989c-7875ba0def33",
                    "include_player_ids": ["ID_DEL_JUEGO"],
                    "contents": {"en": "Tu reserva ha sido exitosa!"},
                    "data": {"type": "reservation_success", "reservation_id": "$reservationId"}
                }
            """.trimIndent()

            sendNotification(notificationJson)
        }
    }

    private fun simulatePaymentConfirmationNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val paymentId = "67890" // Simula un ID de pago
            val notificationJson = """
                {
                    "app_id": "86ae695b-f765-4e4c-989c-7875ba0def33",
                    "include_player_ids": ["ID_DEL_JUEGO"],
                    "contents": {"en": "El pago ha sido confirmado!"},
                    "data": {"type": "payment_confirmation", "payment_id": "$paymentId"}
                }
            """.trimIndent()

            sendNotification(notificationJson)
        }
    }

    private fun simulatePickupReminderNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val notificationJson = """
                {
                    "app_id": "86ae695b-f765-4e4c-989c-7875ba0def33",
                    "include_player_ids": ["ID_DEL_JUEGO"],
                    "contents": {"en": "Tienes 30 minutos para retirar tu comida. No hay reembolsos."},
                    "data": {"type": "pickup_reminder"}
                }
            """.trimIndent()

            sendNotification(notificationJson)
        }
    }

    private val client = OkHttpClient()

    private fun sendNotification(notificationJson: String) {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body: RequestBody = notificationJson.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://onesignal.com/api/v1/notifications")
            .post(body)
            .addHeader("Authorization", "NzJmMWMxNzktM2RiMS00NzY1LTgwZTItYTc0NDE1NGRkNWFm") // Considera usar variables de entorno
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            when (response.code) {
                in 200..299 -> println("Notificación enviada exitosamente.")
                400 -> throw IOException("Solicitud mal formada.")
                401 -> throw IOException("No autorizado.")
                else -> throw IOException("Error inesperado: ${response.code}")
            }
            println(response.body?.string())
        }
    }


}
