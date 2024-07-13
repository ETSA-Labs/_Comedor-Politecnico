package com.espoch.comedor

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.espoch.comedor.shared.services.QrService

class ReservationConfirmationActivity : AppCompatActivity() {

    private lateinit var tvLugar: TextView
    private lateinit var tvPedido: TextView
    private lateinit var tvFecha: TextView
    private lateinit var tvHora: TextView
    private lateinit var tvCantidad: TextView
    private lateinit var ivQRcode: ImageView
    private lateinit var btnEditarReserva: Button
    private lateinit var btnCancelarReserva: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reservation_conf)

        initViews()
        loadReservationDetails()
        generateQRCode()
        setupButtons()
    }

    private fun initViews() {
        tvLugar = findViewById(R.id.tvLugar)
        tvPedido = findViewById(R.id.tvPedido)
        tvFecha = findViewById(R.id.tvFecha)
        tvHora = findViewById(R.id.tvHora)
        tvCantidad = findViewById(R.id.tvCantidad)
        ivQRcode = findViewById(R.id.ivQRCode)
        btnEditarReserva = findViewById(R.id.btnEditarReserva)
        btnCancelarReserva = findViewById(R.id.btnCancelarReserva)
    }

    private fun loadReservationDetails() {
        tvLugar.text = intent.getStringExtra("lugar") ?: "Comedor politécnico"
        tvPedido.text = intent.getStringExtra("pedido") ?: "Desayuno politécnico"
        tvFecha.text = intent.getStringExtra("fecha") ?: "Fecha no disponible"
        tvHora.text = intent.getStringExtra("hora") ?: "Hora no disponible"
        tvCantidad.text = intent.getStringExtra("cantidad") ?: "1"
    }

    private fun generateQRCode() {
        val qrContent = "ReservationID:${System.currentTimeMillis()}" // Generate a unique identifier
        val qrCodeDrawable = QrService.generate(this, qrContent)
        ivQRcode.setImageDrawable(qrCodeDrawable)
    }

    private fun setupButtons() {
        btnEditarReserva.setOnClickListener {
            // Implement edit reservation logic
            Toast.makeText(this, "Editar reserva", Toast.LENGTH_SHORT).show()
        }

        btnCancelarReserva.setOnClickListener {
            // Implement cancel reservation logic
            Toast.makeText(this, "Cancelar reserva", Toast.LENGTH_SHORT).show()
        }
    }
}
