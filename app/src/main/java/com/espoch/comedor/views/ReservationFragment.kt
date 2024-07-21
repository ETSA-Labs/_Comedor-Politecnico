package com.espoch.comedor.views

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.espoch.comedor.R
import com.espoch.comedor.ReservationConfirmationActivity
import com.espoch.comedor.models.Reservation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ReservationFragment : AppCompatActivity() {
    private lateinit var etPrice: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var btnConfirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reservation)
        etPrice = findViewById(R.id.etPrice)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        btnConfirm = findViewById(R.id.btnConfirm)

        etDate.setOnClickListener { showDatePicker() }
        etTime.setOnClickListener { showTimePicker() }

        btnConfirm.setOnClickListener {
            Log.d("ReservationFragment", "Botón reservar clicado")
            crearCanalNotificacion("Reserva Exitosa","Canal de Reserva")
            crearNotificacion("Reserva Exitosa", "Su reserva se ha realizado exitosamente.",1)

            if (validateInputs()) {
                // Aquí se puede agregar la lógica para reservar el comedor
                // Por ejemplo, puedes mostrar un mensaje de éxito o navegar a otra pantalla
                saveReservation()
                startActivity(Intent(this, ReservationDetailsActivity::class.java))
            }
        }
        setupButtonNavigation()
    }
    //Notificacion Llamado
    private fun crearCanalNotificacion(canalId: String, canalNombre: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canalImportancia = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel(canalId, canalNombre, canalImportancia)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }
    private fun crearNotificacion(titulo: String, texto: String, idNotificacion: Int) {
        val builder = NotificationCompat.Builder(this, "Reserva Exitosa")
            .setSmallIcon(R.drawable.ic_polidish_24dp)
            .setContentTitle(titulo)
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(this@ReservationFragment, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Log.d("ReservationFragment", "Permiso de notificaciones no concedido")
                return
            }
            notify(idNotificacion, builder.build())
        }
    }




    private fun validateInputs(): Boolean {
        if (etPrice.text.isNullOrBlank() || etDate.text.isNullOrBlank() || etTime.text.isNullOrBlank()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveReservation() {
        //CONSIDERAR ESTAS INSTANCIAS DEFINIDAS PARA EL METODO
        //POKE NO SE SI SE ESTAN LLAMANDO A LOS OTRAS CLASES DEFINIDAS
        //INICIA AQUI
        val price = etPrice.text.toString()
        val date = etDate.text.toString()
        val time = etTime.text.toString()

        val intent = Intent(this, ReservationConfirmationActivity::class.java)

        val newReservation = Reservation("Desayuno Politecnico", date, time, 1)
        val currentReservations = PreferencesUtilFragment.getReservation(this)
        val updatedReservations = currentReservations + newReservation

        PreferencesUtilFragment.saveReservation(this, updatedReservations)
        // Aquí puedes agregar la lógica para guardar la reserva en la base de datos. Por ejemplo, puedes
        // utilizar una biblioteca de base de datos como Firebase o una base de datos local como SQLite
        Toast.makeText(this, "Reserva confirmada", Toast.LENGTH_SHORT).show()
        //TERMINA AQUI

        intent.putExtra("lugar", "Comedor politécnico")
        intent.putExtra("pedido", "Desayuno politécnico")
        intent.putExtra("fecha", etDate.text.toString())
        intent.putExtra("hora", etTime.text.toString())
        intent.putExtra("cantidad", "1")
        startActivity(intent)
        finish() // Optional: close the reservation activity
    }

    private fun setupButtonNavigation() {
        findViewById<ImageButton>(R.id.navigation_home).setOnClickListener { /* Navigate to home */ }
        findViewById<ImageButton>(R.id.navigation_favourites).setOnClickListener { /* Navigate to favorites */ }
        findViewById<ImageButton>(R.id.navigation_booking).setOnClickListener { /* Navigate to calendar */ }
        findViewById<ImageButton>(R.id.navigation_profile).setOnClickListener { /* Navigate to profile */ }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            etDate.setText(dateFormat.format(selectedDate.time))
        }, year, month, day).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            etTime.setText(selectedTime)
        }, hour, minute, true).show()
    }
}