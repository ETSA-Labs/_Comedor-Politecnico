package com.espoch.comedor

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.espoch.comedor.databinding.ActivityLoginBinding
import com.espoch.comedor.shared.models.AppUser
import com.espoch.comedor.shared.services.AuthService

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incRequestSignin.btnMicrosoft.setOnClickListener(::onMicrosoftButtonClick)

        AuthService.addResultListener(AuthResultCallback())

        onBackPressedDispatcher.addCallback(this, OnBackInvokedCallback())
    }

    private fun onMicrosoftButtonClick(v: View) {
        AuthService.signIn()
    }

    private inner class AuthResultCallback : AuthService.ResultListener() {
        override fun onSignIn() {
            super.onSignIn()

            binding.incRequestSignin.btnMicrosoft.isEnabled = false

            this@LoginActivity.finish()
        }
    }

    private inner class OnBackInvokedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            this@LoginActivity.finishAffinity()
        }
    }
}

/*
// Obtén una referencia al Spinner
        val spinner: Spinner = findViewById(R.id.spinner)

        // Crea una lista de objetos Item para el Spinner
        val items = listOf(
            Item(1, "Item 1", "Description 1"),
            Item(2, "Item 2", "Description 2"),
            Item(3, "Item 3", "Description 3"),
            Item(4, "Item 4", "Description 4")
        )

        // Crea un adaptador personalizado utilizando la lista de objetos Item
        val adapter = ItemAdapter(this, items)

        // Asigna el adaptador al Spinner
        spinner.adapter = adapter

        // Maneja la selección de elementos
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as Item
                Toast.makeText(this@MainActivity, "Selected: ${selectedItem.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acciones a realizar cuando no se selecciona ningún elemento (opcional)
            }
        }
 */