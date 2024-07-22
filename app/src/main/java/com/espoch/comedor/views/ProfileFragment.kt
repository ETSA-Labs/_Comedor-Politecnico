package com.espoch.comedor.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.espoch.comedor.databinding.FragmentProfileBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 71
    private var imageUri: Uri? = null
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        loadUserProfile()

        // Configurar el botón de guardar cambios
        binding.saveButton.setOnClickListener {
            saveUserProfile()
        }

        // Configurar el botón de cambiar foto
        binding.changePhoto.setOnClickListener {
            chooseImage()
        }

        return view
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.profileImage.setImageURI(imageUri)
        }
    }

    private fun loadUserProfile() {
        val sharedPreferences = activity?.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
        val userName = sharedPreferences?.getString("USER_NAME", "Nombre no disponible")
        val userEmail = sharedPreferences?.getString("USER_EMAIL", "Correo no disponible")
        val userId = sharedPreferences?.getString("USER_ID", "ID no disponible")

        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(userId)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val fullName = document.getString("fullName")
                        val email = document.getString("email")
                        val faculty = document.getString("faculty")
                        val career = document.getString("career")
                        val profileImageUrl = document.getString("profileImageUrl")

                        binding.userName.setText(fullName ?: "Nombre no disponible")
                        binding.userEmail.setText(email ?: "Correo no disponible")
                        binding.userFaculty.setText(faculty ?: "")
                        binding.userCareer.setText(career ?: "")

                        if (!profileImageUrl.isNullOrEmpty()) {
                            // Cargar la imagen usando Glide u otra librería
                            Glide.with(this).load(profileImageUrl).into(binding.profileImage)
                        }
                    } else {
                        Log.d("Firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", "get failed with ", exception)
                }
        } else {
            Log.d("SharedPreferences", "User ID not found in SharedPreferences")
        }
    }

    private fun saveUserProfile() {
        val sharedPreferences = activity?.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("USER_ID", "ID no disponible")

        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(userId)

            val userUpdates = hashMapOf(
                "faculty" to binding.userFaculty.text.toString(),
                "career" to binding.userCareer.text.toString()
            )

            userRef.update(userUpdates as Map<String, Any>)
                .addOnSuccessListener {
                    // Ocultar el teclado
                    hideKeyboard()

                    // Quitar el foco de los EditText
                    binding.userFaculty.clearFocus()
                    binding.userCareer.clearFocus()

                    // Mostrar mensaje de éxito
                    Toast.makeText(context, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show()

                    // Subir imagen si una nueva ha sido seleccionada
                    if (imageUri != null) {
                        uploadImage(userId)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", "Error updating profile", exception)
                }
        }
    }

    private fun uploadImage(userId: String) {
        if (imageUri != null) {
            val ref = storageReference.child("images/${UUID.randomUUID()}")
            ref.putFile(imageUri!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        saveImageUrlToFirestore(userId, imageUrl)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Failed to upload image", e)
                }
        }
    }

    private fun saveImageUrlToFirestore(userId: String, imageUrl: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)

        userRef.update("profileImageUrl", imageUrl)
            .addOnSuccessListener {
                Log.d("Firebase", "Profile image URL updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Failed to update profile image URL", e)
            }
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
