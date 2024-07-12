package com.espoch.comedor.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.MainActivity
import com.espoch.comedor.MapsActivity
import com.espoch.comedor.R
import com.espoch.comedor.databinding.FragmentMoreBinding
import com.espoch.comedor.extensions.isLightStatusBar
import com.google.android.material.button.MaterialButton

class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.isLightStatusBar = true

        // Configurar el OnClickListener para el botón de "Location"
        binding.bottomButtons.findViewById<MaterialButton>(R.id.location_button).setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
