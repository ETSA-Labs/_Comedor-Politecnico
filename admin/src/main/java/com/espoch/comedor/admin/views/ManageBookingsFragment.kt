package com.espoch.comedor.admin.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.admin.databinding.FragmentManageBookingsBinding

class ManageBookingsFragment : Fragment() {

    private lateinit var binding: FragmentManageBookingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageBookingsBinding.inflate(inflater)
        return binding.root
    }
}

/*
private fun loadExistingReservations() {
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
    }
    */