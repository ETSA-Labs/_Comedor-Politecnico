package com.espoch.comedor.views

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.espoch.comedor.R

class ProfileFragment : AppCompatActivity() {
    private lateinit var ivProfileImage: ImageView
    private lateinit var tvProfileTitle: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvFaculty: TextView
    private lateinit var tvDepartment: TextView
    private lateinit var changePhotoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        ivProfileImage = findViewById(R.id.iv_profile_image)
        tvProfileTitle = findViewById(R.id.tv_profile_title)
        tvName = findViewById(R.id.tv_name)
        tvEmail = findViewById(R.id.tv_email)
        tvFaculty = findViewById(R.id.tv_faculty)
        tvDepartment = findViewById(R.id.tv_department)
        changePhotoButton = findViewById(R.id.changePhotoButton)

        changePhotoButton.setOnClickListener {
            // Lógica para cambiar la foto de perfil
        }
    }
}
