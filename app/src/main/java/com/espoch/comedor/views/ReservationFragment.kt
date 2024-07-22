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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.espoch.comedor.R
import com.espoch.comedor.ReservationConfirmationActivity
import com.espoch.comedor.models.Reservation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReservationFragment : Fragment() {
    private lateinit var etPrice: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var btnConfirm: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservation, container, false)
        etPrice = view.findViewById(R.id.etPrice)
        etDate = view.findViewById(R.id.etDate)
        etTime = view.findViewById(R.id.etTime)
        btnConfirm = view.findViewById(R.id.btnConfirm)

        etDate.setOnClickListener { showDatePicker() }
        etTime.setOnClickListener { showTimePicker() }

        btnConfirm.setOnClickListener {
            Log.d("ReservationFragment", "Botón reservar clicado")
            crearCanalNotificacion("Reserva Exitosa", "Canal de Reserva")
            crearNotificacion("Reserva Exitosa", "Su reserva se ha realizado exitosamente.", 1)

            if (validateInputs()) {
                saveReservation()
                startActivity(Intent(requireContext(), ReservationDetailsActivity::class.java))
            }
        }

        setupButtonNavigation(view)

        return view
    }

    // Notificación Llamado
    private fun crearCanalNotificacion(canalId: String, canalNombre: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canalImportancia = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel(canalId, canalNombre, canalImportancia)
            val manager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }

    private fun crearNotificacion(titulo: String, texto: String, idNotificacion: Int) {
        val builder = NotificationCompat.Builder(requireContext(), "Reserva Exitosa")
            .setSmallIcon(R.drawable.ic_polidish_24dp)
            .setContentTitle(titulo)
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Log.d("ReservationFragment", "Permiso de notificaciones no concedido")
                return
            }
            notify(idNotificacion, builder.build())
        }
    }

    private fun validateInputs(): Boolean {
        if (etPrice.text.isNullOrBlank() || etDate.text.isNullOrBlank() || etTime.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveReservation() {
        val price = etPrice.text.toString()
        val date = etDate.text.toString()
        val time = etTime.text.toString()

        val intent = Intent(requireContext(), ReservationConfirmationActivity::class.java)

        val newReservation = Reservation("Desayuno Politecnico", date, time, 1)
        val currentReservations = PreferencesUtilFragment.getReservation(requireContext())
        val updatedReservations = currentReservations + newReservation

        PreferencesUtilFragment.saveReservation(requireContext(), updatedReservations)

        Toast.makeText(requireContext(), "Reserva confirmada", Toast.LENGTH_SHORT).show()

        intent.putExtra("lugar", "Comedor politécnico")
        intent.putExtra("pedido", "Desayuno politécnico")
        intent.putExtra("fecha", etDate.text.toString())
        intent.putExtra("hora", etTime.text.toString())
        intent.putExtra("cantidad", "1")
        startActivity(intent)
    }

    private fun setupButtonNavigation(view: View) {
        view.findViewById<ImageButton>(R.id.navigation_home).setOnClickListener { /* Navigate to home */ }
        view.findViewById<ImageButton>(R.id.navigation_mapa).setOnClickListener { /* Navigate to favorites */ }
        //view.findViewById<ImageButton>(R.id.navigation_booking).setOnClickListener { /* Navigate to calendar */ }
        view.findViewById<ImageButton>(R.id.navigation_profile).setOnClickListener { /* Navigate to profile */ }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
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

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            etTime.setText(selectedTime)
        }, hour, minute, true).show()
    }
}
