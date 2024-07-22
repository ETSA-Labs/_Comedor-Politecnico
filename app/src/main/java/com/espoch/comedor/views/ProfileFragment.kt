package com.espoch.comedor.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        loadUserProfile()

        // Configurar el botón de cambiar foto
        binding.changePhoto.setOnClickListener {
            // Aquí puedes implementar la funcionalidad para cambiar la foto
        }

        return view
    }

    private fun loadUserProfile() {
        val sharedPreferences = activity?.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
        val userName = sharedPreferences?.getString("USER_NAME", "Nombre no disponible")
        val userEmail = sharedPreferences?.getString("USER_EMAIL", "Correo no disponible")

        binding.userName.setText(userName)
        binding.userEmail.setText(userEmail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
