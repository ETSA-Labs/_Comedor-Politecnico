package com.espoch.comedor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.espoch.comedor.R

class ProfileFragment_ : Fragment() {

    private lateinit var tvProfileTitle: TextView
    private lateinit var ivProfileImage: ImageView
    private lateinit var tvAboutYou: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvFaculty: TextView
    private lateinit var tvDepartment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Aquí puedes manejar los parámetros si los necesitas
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_, container, false)

        // Inicializar las vistas
        tvProfileTitle = view.findViewById(R.id.tv_profile_title)
        ivProfileImage = view.findViewById(R.id.iv_profile_image)
        tvAboutYou = view.findViewById(R.id.tv_about_you)
        tvName = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        tvFaculty = view.findViewById(R.id.tv_faculty)
        tvDepartment = view.findViewById(R.id.tv_department)

        // Ejemplo de actualización de texto
        tvName.text = "Melany Orna"

        // Si necesitas cambiar la imagen, puedes hacerlo aquí
        // ivProfileImage.setImageResource(R.drawable.nueva_imagen)

        return view
    }
}
