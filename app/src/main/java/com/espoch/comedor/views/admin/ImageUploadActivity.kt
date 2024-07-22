package com.espoch.comedor.views.admin
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.espoch.comedor.databinding.ActivityImageUploadBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class ImageUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageUploadBinding
    private val storage: FirebaseStorage by lazy { Firebase.storage }
    private var selectedImageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val IMAGE_UPLOAD_PATH = "menu_images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el botón para seleccionar la imagen
        binding.btnSelectImage.setOnClickListener {
            openImagePicker()
        }

        // Configura el botón para subir la imagen
        binding.btnUploadImage.setOnClickListener {
            if (selectedImageUri != null) {
                uploadImage()
            } else {
                Toast.makeText(this, "Por favor, selecciona una imagen primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun uploadImage() {
        val fileUri = selectedImageUri ?: return
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val storageRef = storage.reference.child(IMAGE_UPLOAD_PATH + fileName)
        val uploadTask = storageRef.putFile(fileUri)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val resultIntent = Intent().apply {
                    putExtra("IMAGE_URL", uri.toString())
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            binding.ivImagePreview.setImageURI(selectedImageUri)
            binding.ivImagePreview.visibility = View.VISIBLE
        }
    }
}
