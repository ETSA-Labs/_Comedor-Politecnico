package com.espoch.comedor.admin

import com.espoch.comedor.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.auth.User

class GestionarRolesActivity : AppCompatActivity() {
    private lateinit var spinnerSeleccionarUsuario: Spinner
    private lateinit var etNombreUsuario: EditText
    private lateinit var etEmailUsuario: EditText
    private lateinit var rgEstadoAdmin: RadioGroup
    private lateinit var btnCancelar: Button
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_roles)

        /*initializeViews()
        setupSpinner()
        setupButtonListeners()
        loadExistingUsers()

        spinnerSeleccionarUsuario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedUser = parent.getItemAtPosition(position).toString()
                loadUserData(selectedUser)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }*/
    }

    /*private fun loadUserData(userName: String) {
        // Aquí cargarías los datos del usuario seleccionado
        // Por ahora, simularemos con datos ficticios
        val user = User("Usuario 1", "usuario1@example.com", true)
        etNombreUsuario.setText(user.name)
        etEmailUsuario.setText(user.email)
        if (user.isAdmin) {
            rgEstadoAdmin.check(R.id.rbSi)
        } else {
            rgEstadoAdmin.check(R.id.rbNo)
        }

    }

    private fun loadExistingUsers() {
        // Aquí cargarías los usuarios desde tu base de datos o API
        // Por ahora, simularemos con datos ficticios
        val users = listOf("Usuario 1", "Usuario 2", "Usuario 3")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeleccionarUsuario.adapter = adapter
    }

    private fun initializeViews() {
        spinnerSeleccionarUsuario = findViewById(R.id.spinnerSeleccionarUsuario)
        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etEmailUsuario = findViewById(R.id.etEmailUsuario)
        rgEstadoAdmin = findViewById(R.id.rgEstadoAdmin)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnGuardar = findViewById(R.id.btnGuardar)
    }

    private fun setupSpinner() {
        val usuarios = arrayOf("Usuario 1", "Usuario 2", "Usuario 3") // Replace with actual user data
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, usuarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeleccionarUsuario.adapter = adapter
    }

    private fun setupButtonListeners() {
        btnCancelar.setOnClickListener {
            // Implement cancel functionality
            clearInputs()
            finish()

            btnGuardar.setOnClickListener {
                if (validateInputs()) {
                    saveUserRole()
                }
            }
        }

        btnGuardar.setOnClickListener {
            // Implement save functionality
            val nombre = etNombreUsuario.text.toString()
            val email = etEmailUsuario.text.toString()
            val esAdmin = rgEstadoAdmin.checkedRadioButtonId == R.id.rbSi

            // Here you would typically save this data to your backend or database
            Toast.makeText(this, "Guardando cambios...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(): Boolean {
        if (etNombreUsuario.text.isEmpty() || etEmailUsuario.text.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveUserRole(){
        val nombre = etNombreUsuario.text.toString()
        val email = etEmailUsuario.text.toString()
        val esAdmin = rgEstadoAdmin.checkedRadioButtonId == R.id.rbSi

        // Here you would typically save this data to your backend or database
        // For this example, we'll just show a success message
        Toast.makeText(this, "Rol de usuario guardado exitosamente", Toast.LENGTH_SHORT).show()
        clearInputs()
    }

    private fun clearInputs() {
        etNombreUsuario.text.clear()
        etEmailUsuario.text.clear()
        rgEstadoAdmin.clearCheck()
        spinnerSeleccionarUsuario.setSelection(0)
    }*/
}