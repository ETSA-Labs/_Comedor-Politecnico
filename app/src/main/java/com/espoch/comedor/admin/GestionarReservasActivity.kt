package com.espoch.comedor.admin

import com.espoch.comedor.R
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.espoch.comedor.views.Reservation
import java.io.File
import java.io.FileWriter

class GestionarReservasActivity : AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etCantidad: EditText
    private lateinit var spinnerEstado: Spinner
    private lateinit var btnGenerarReporte: Button

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_gestionar_reservas)

        /*initializeViews()
        setupSpinner()
        setupButtonListener()
        loadExistingReservations()*/
    }

    /*private fun loadExistingReservations() {
        // Aquí cargarías las reservas desde tu base de datos o API
        // Por ahora, simularemos con datos ficticios
        val reservation = Reservation("Usuario Ejemplo", 2, "pendiente")
        etUsuario.setText(reservation.userName)
        etCantidad.setText(reservation.quantity.toString())
        // Establecer el estado en el spinner
    }

    private fun initializeViews() {
        etUsuario = findViewById(R.id.etUsuario)
        etCantidad = findViewById(R.id.etCantidad)
        spinnerEstado = findViewById(R.id.spinnerEstado)
        btnGenerarReporte = findViewById(R.id.btnGenerarReporte)
    }

    private fun setupSpinner() {
        val estados = arrayOf("No entregada", "pendiente", "entregada")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapter
    }

    private fun setupButtonListener() {
        btnGenerarReporte.setOnClickListener {
            // Implement report generation functionality
            generateReport()

            Toast.makeText(this, "Generando reporte...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateReport() {
        val reportContent = StringBuilder()
        reportContent.append("Reporte de Reservas\n\n")
        reportContent.append("Usuario: ${etUsuario.text}\n")
        reportContent.append("Cantidad: ${etCantidad.text}\n")
        reportContent.append("Estado: ${spinnerEstado.selectedItem}\n")

        // Add more details to the report as needed

        // Save the report to a file
        val fileName = "reporte_reservas_${System.currentTimeMillis()}.txt"
        val file = File(getExternalFilesDir(null), fileName)

        try {
            FileWriter(file).use { writer ->
                writer.append(reportContent)
            }
            Toast.makeText(this, "Reporte generado: $fileName", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al generar el reporte", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }*/
}