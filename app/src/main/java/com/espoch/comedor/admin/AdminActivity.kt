package com.espoch.comedor.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.espoch.comedor.R
import com.google.zxing.integration.android.IntentIntegrator

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btnGestionarMenu: Button = findViewById(R.id.btnGestionarMenu)
        val btnEscaneoQR: Button = findViewById(R.id.btnEscaneoQR)
        val btnGestionarReservas: Button = findViewById(R.id.btnGestionarReservas)
        val btnGestionarRoles: Button = findViewById(R.id.btnGestionarRoles)

        btnGestionarMenu.setOnClickListener {
            // Implement menu management functionality
            startActivity(Intent(this, GestionarMenuActivity::class.java))

            Toast.makeText(this, "Gestionar Menú clicked", Toast.LENGTH_SHORT).show()
        }

        btnEscaneoQR.setOnClickListener {
            // Implement QR code scanning functionality
            startActivity(Intent(this, EscanearQRActivity::class.java))

            Toast.makeText(this, "Escaneo de Códigos QR clicked", Toast.LENGTH_SHORT).show()
        }

        btnGestionarReservas.setOnClickListener {
            // Implement reservation management functionality
            startActivity(Intent(this, GestionarReservasActivity::class.java))

            Toast.makeText(this, "Gestionar Reservas clicked", Toast.LENGTH_SHORT).show()
        }

        btnGestionarRoles.setOnClickListener {
            // Implement role management functionality
            startActivity(Intent(this, GestionarRolesActivity::class.java))

            Toast.makeText(this, "Gestionar Roles clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Escaneado: " + result.contents, Toast.LENGTH_LONG).show()
                // Process the scanned QR code content here
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}