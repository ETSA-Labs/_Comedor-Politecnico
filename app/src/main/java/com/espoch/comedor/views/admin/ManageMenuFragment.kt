package com.espoch.comedor.views.admin

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.espoch.comedor.R
import com.espoch.comedor.databinding.FragmentManageMenuBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class ManageMenuFragment : Fragment() {

    private lateinit var binding: FragmentManageMenuBinding
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private var imageUrl: String = ""
    private val IMAGE_UPLOAD_REQUEST_CODE = 2000

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
            chooseDish("Actualizar")
        }

        // Manejar el evento del botón Eliminar
        binding.btnEliminar.setOnClickListener {
            chooseDish("Eliminar")
        }

        // Manejar el evento del botón Seleccionar Imagen
        binding.btnSeleccionarImagen.setOnClickListener {
            openImageUploadActivity()
        }
    }

    private fun addDish() {
        val category = binding.spinnerCategoria.selectedItem.toString()
        val name = binding.etNombrePlato.text.toString()
        val description = binding.etDescripcion.text.toString()
        val price = "1.50" // Precio predeterminado
        val availability = true // Puedes ajustar esto según tu lógica de disponibilidad

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
                "imagen" to imageUrl,
                "categoria" to category // Asegúrate de incluir la categoría aquí
            )

            firestore.collection("menu")
                .add(dish)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Plato añadido con éxito", Toast.LENGTH_SHORT).show()
                    clearFields() // Limpiar todos los campos, excepto el precio
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error al añadir plato", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }


    private fun chooseDish(action: String) {
        val category = binding.spinnerCategoria.selectedItem.toString()

        firestore.collection("menu")
            .whereEqualTo("categoria", category)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(requireContext(), "No hay platos disponibles en esta categoría", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val dishNames = documents.map { it.getString("nombre_plato") ?: "" }
                if (dishNames.isEmpty()) {
                    Toast.makeText(requireContext(), "No hay platos disponibles en esta categoría", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("Selecciona el plato a $action")
                dialogBuilder.setItems(dishNames.toTypedArray()) { _, which ->
                    val selectedDishName = dishNames[which]
                    if (action == "Actualizar") {
                        openEditDishActivity(category, selectedDishName)
                    } else {
                        openConfirmDeleteActivity(category, selectedDishName)
                    }
                }
                dialogBuilder.create().show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al cargar los platos", Toast.LENGTH_SHORT).show()
            }
    }



    private fun openEditDishActivity(category: String, dishName: String) {
        val query = firestore.collection("menu")
            .whereEqualTo("categoria", category)
            .whereEqualTo("nombre_plato", dishName)

        query.get().addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                Toast.makeText(requireContext(), "No se encontró el plato", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            val dishId = documents.documents.first().id
            val intent = Intent(requireContext(), EditDishActivity::class.java)
            intent.putExtra("DISH_ID", dishId)
            intent.putExtra("CATEGORY", category)
            startActivity(intent)
        }
    }


    private fun openConfirmDeleteActivity(category: String, dishName: String) {
        val query = firestore.collection("menu")
            .whereEqualTo("categoria", category)
            .whereEqualTo("nombre_plato", dishName)

        query.get().addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                Toast.makeText(requireContext(), "No se encontró el plato", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            val dishId = documents.documents.first().id
            val intent = Intent(requireContext(), ConfirmDeleteActivity::class.java)
            intent.putExtra("DISH_ID", dishId)
            startActivity(intent)
        }
    }

    private fun openImageUploadActivity() {
        val intent = Intent(requireContext(), ImageUploadActivity::class.java)
        startActivityForResult(intent, IMAGE_UPLOAD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_UPLOAD_REQUEST_CODE) {
            imageUrl = data?.getStringExtra("IMAGE_URL") ?: ""
            if (imageUrl.isNotEmpty()) {
                binding.btnSeleccionarImagen.text = "Imagen seleccionada"
                binding.tvImagenVerificada.text = "Imagen verificada"
                binding.tvImagenVerificada.setTextColor(resources.getColor(R.color.colorGreen, null))
            } else {
                binding.tvImagenVerificada.text = "Imagen no verificada"
                binding.tvImagenVerificada.setTextColor(resources.getColor(R.color.colorRed, null))
            }
        }
    }

    private fun clearFields() {
        binding.etNombrePlato.text.clear()
        binding.etDescripcion.text.clear()
        // No limpiar el campo de precio
        // binding.etPrecio.text.clear()
        binding.spinnerCategoria.setSelection(0)
        binding.btnSeleccionarImagen.text = "Seleccionar Imagen"
        imageUrl = ""
    }
}
