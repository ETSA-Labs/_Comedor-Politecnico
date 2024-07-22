package com.espoch.comedor.views.admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.espoch.comedor.R
import com.google.firebase.firestore.FirebaseFirestore

class EditDishActivity : AppCompatActivity() {

    private lateinit var editTextDishName: EditText
    private lateinit var editTextDishDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var buttonSelectImage: Button
    private lateinit var textViewImageStatus: TextView
    private lateinit var buttonUpdateDish: Button

    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var dishId: String
    private var imageUrl: String = ""
    private val IMAGE_UPLOAD_REQUEST_CODE = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_dish)

        editTextDishName = findViewById(R.id.editTextDishName)
        editTextDishDescription = findViewById(R.id.editTextDishDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        buttonSelectImage = findViewById(R.id.buttonSelectImage)
        textViewImageStatus = findViewById(R.id.textViewImageStatus)
        buttonUpdateDish = findViewById(R.id.buttonUpdateDish)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dishId = intent.getStringExtra("DISH_ID") ?: ""
        loadDishData()

        buttonUpdateDish.setOnClickListener {
            updateDish()
        }

        buttonSelectImage.setOnClickListener {
            openImageUploadActivity()
        }
    }

    private fun loadDishData() {
        firestore.collection("menu").document(dishId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    editTextDishName.setText(document.getString("nombre_plato"))
                    editTextDishDescription.setText(document.getString("descripcion"))
                    spinnerCategory.setSelection(getCategoryPosition(document.getString("categoria")))
                    imageUrl = document.getString("imagen") ?: ""
                    // Aquí podrías cargar la imagen si es necesario
                }
            }
    }

    private fun updateDish() {
        val name = editTextDishName.text.toString()
        val description = editTextDishDescription.text.toString()
        val category = spinnerCategory.selectedItem.toString()

        if (name.isNotEmpty() && description.isNotEmpty()) {
            val dishUpdates = mapOf(
                "nombre_plato" to name,
                "descripcion" to description,
                "categoria" to category,
                "imagen" to imageUrl
            )

            firestore.collection("menu").document(dishId)
                .update(dishUpdates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Plato actualizado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar plato", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCategoryPosition(category: String?): Int {
        val categories = listOf("Desayuno", "Almuerzo", "Merienda")
        return categories.indexOf(category ?: "Desayuno")
    }

    private fun openImageUploadActivity() {
        val intent = Intent(this, ImageUploadActivity::class.java)
        startActivityForResult(intent, IMAGE_UPLOAD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_UPLOAD_REQUEST_CODE) {
            imageUrl = data?.getStringExtra("IMAGE_URL") ?: ""
            if (imageUrl.isNotEmpty()) {
                buttonSelectImage.text = "Imagen seleccionada"
                textViewImageStatus.text = "Imagen verificada"
                textViewImageStatus.setTextColor(resources.getColor(R.color.colorGreen, null))
            } else {
                textViewImageStatus.text = "Imagen no verificada"
                textViewImageStatus.setTextColor(resources.getColor(R.color.colorRed, null))
            }
        }
    }
}
