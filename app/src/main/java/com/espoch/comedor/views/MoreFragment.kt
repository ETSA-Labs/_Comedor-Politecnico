package com.espoch.comedor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.MainActivity
import com.espoch.comedor.databinding.FragmentMoreBinding
import com.espoch.comedor.extensions.isLightStatusBar
import com.espoch.comedor.models.AppUser

class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.isLightStatusBar = true

        binding.btnAdminPanel.visibility =
            if (AppUser.default.role == AppUser.UserRole.ADMIN)
                View.VISIBLE
            else View.GONE
    }
}