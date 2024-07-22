package com.espoch.comedor.views.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.espoch.comedor.databinding.FragmentManageMenuBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class ManageMenuFragment : Fragment() {

    private lateinit var binding: FragmentManageMenuBinding
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private val storage: FirebaseStorage by lazy { Firebase.storage }
    private var selectedImageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val IMAGE_UPLOAD_PATH = "menu_images/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el Spinner con categorías (Desayuno, Almuerzo, Merienda)
        val categories = listOf("Desayuno", "Almuerzo", "Merienda")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        binding.spinnerCategoria.adapter = adapter

        // Manejar el evento del botón Añadir
        binding.btnAnadir.setOnClickListener {
            addDish()
        }

        // Manejar el evento del botón Actualizar
        binding.btnActualizar.setOnClickListener {
            updateDish()
        }

        // Manejar el evento del botón Eliminar
        binding.btnEliminar.setOnClickListener {
            deleteDish()
        }

        // Manejar el evento del botón Seleccionar Imagen
        binding.btnSeleccionarImagen.setOnClickListener {
            openImagePicker()
        }
    }

    private fun addDish() {
        val category = binding.spinnerCategoria.selectedItem.toString()
        val name = binding.etNombrePlato.text.toString()
        val description = binding.etDescripcion.text.toString()
        val price = "1.50" // Precio predeterminado
        val availability = true // Puedes ajustar esto según tu lógica de disponibilidad
        val imageUrl = if (selectedImageUri != null) uploadImageAndGetUrl() else ""

        if (name.isNotEmpty() && description.isNotEmpty()) {
            val menuId = UUID.randomUUID().toString() // Genera un ID único para el menú
            val date = System.currentTimeMillis() // Usa la fecha actual en milisegundos

            val dish = mapOf(
                "menu_id" to menuId,
                "fecha" to date,
                "nombre_plato" to name,
                "descripcion" to description,
                "precio" to price,
                "disponibilidad" to availability,
                "imagen" to imageUrl
            )

            firestore.collection("menu")
                .add(dish)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Plato añadido con éxito", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error al añadir plato", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDish() {
        // Implementa la lógica para actualizar un plato, usando menu_id para identificar el documento
    }

    private fun deleteDish() {
        // Implementa la lógica para eliminar un plato, usando menu_id para identificar el documento
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun uploadImageAndGetUrl(): String {
        val fileUri = selectedImageUri ?: return ""
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val storageRef = storage.reference.child(IMAGE_UPLOAD_PATH + fileName)
        val uploadTask = storageRef.putFile(fileUri)

        var imageUrl = ""
        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                // Devuelve la URL de la imagen
                imageUrl = uri.toString()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }

        return imageUrl
    }

    private fun clearFields() {
        binding.etNombrePlato.text.clear()
        binding.etDescripcion.text.clear()
        binding.etPrecio.text.clear()
        binding.spinnerCategoria.setSelection(0)
        binding.btnSeleccionarImagen.text = "Seleccionar Imagen"
        selectedImageUri = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            binding.btnSeleccionarImagen.text = "Imagen seleccionada"
        }
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