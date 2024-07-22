package com.espoch.comedor.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.R
import com.google.android.material.button.MaterialButton

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        view.findViewById<MaterialButton>(R.id.btn_about).setOnClickListener {
            val intent = Intent(activity, AboutActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<MaterialButton>(R.id.btn_messages).setOnClickListener {
            // Lógica para Messages
        }

        view.findViewById<MaterialButton>(R.id.btn_privacy_policy).setOnClickListener {
            // Lógica para Política de privacidad
        }

        view.findViewById<MaterialButton>(R.id.btn_support).setOnClickListener {
            // Lógica para Support
        }

        view.findViewById<MaterialButton>(R.id.btn_user_guide).setOnClickListener {
            // Lógica para Guía de uso

        }

        view.findViewById<MaterialButton>(R.id.btn_terms_conditions).setOnClickListener {
            // Lógica para Términos y condiciones
        }

        view.findViewById<MaterialButton>(R.id.btn_logout).setOnClickListener {
            // Lógica para Cerrar Sesión
        }

        return view
    }
}
