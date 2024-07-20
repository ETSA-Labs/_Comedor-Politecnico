package com.espoch.comedor.views.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.databinding.FragmentManageMenuBinding

class ManageMenuFragment : Fragment() {

    private lateinit var binding: FragmentManageMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageMenuBinding.inflate(inflater)
        return binding.root
    }
}

/*

private fun loadExistingDishes() {
        // Aquí cargarías los platos desde tu base de datos o API
        // Por ahora, simularemos con datos ficticios
        val dish = Dish()
        dish.let {
            it.name = "Arroz con pollo"
            it.price = 2.25f
        }

        etNombrePlato.setText(dish.name)
        etDescripcion.setText(dish.description)
        etPrecio.setText(dish.price.toString())
        // Establecer la categoría en el spinner
    }

    private fun initializeViews() {
        spinnerCategoria = binding.spinnerCategoria
        etNombrePlato = binding.etNombrePlato
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        etDescripcion = findViewById(R.id.etDescripcion)
        etPrecio = findViewById(R.id.etPrecio)
        btnAnadir = findViewById(R.id.btnAnadir)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
    }

    private fun setupSpinner() {

    }/*val categories = arrayOf("Desayunos", "Almuerzos", "Meriendas")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapter*/

    private fun setupButtonListeners() {
        btnSeleccionarImagen.setOnClickListener {
            // Implement image selection functionality
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)

            Toast.makeText(this, "Seleccionar imagen", Toast.LENGTH_SHORT).show()
        }

        btnAnadir.setOnClickListener {
            // Implement add functionality
            if (validateInputs()) {
                // Add the dish to the database or send to server
                Toast.makeText(this, "Plato añadido exitosamente", Toast.LENGTH_SHORT).show()
                clearInputs()
            }

            Toast.makeText(this, "Añadir plato", Toast.LENGTH_SHORT).show()
        }

        btnActualizar.setOnClickListener {
            // Implement update functionality
            if (validateInputs()) {
                // Update the dish in the database or send to server
                Toast.makeText(this, "Plato actualizado exitosamente", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(this, "Actualizar plato", Toast.LENGTH_SHORT).show()
        }

        btnEliminar.setOnClickListener {
            // Implement delete functionality
            // Delete the dish from the database or send delete request to server
            Toast.makeText(this, "Plato eliminado exitosamente", Toast.LENGTH_SHORT).show()
            clearInputs()

            Toast.makeText(this, "Eliminar plato", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(): Boolean {
        // Add your validation logic here
        return true
    }

    private fun clearInputs() {
        etNombrePlato.text.clear()
        etDescripcion.text.clear()
        etPrecio.text.clear()
        selectedImageUri = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            btnSeleccionarImagen.text = "Imagen seleccionada"
        }
    }
    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

 */