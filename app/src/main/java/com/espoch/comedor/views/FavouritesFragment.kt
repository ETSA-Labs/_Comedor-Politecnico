package com.espoch.comedor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.MainActivity
import com.espoch.comedor.databinding.FragmentFavouritesBinding
import com.espoch.comedor.extensions.isLightStatusBar
import com.espoch.comedor.services.QrService

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.isLightStatusBar = true

        updateQR()

        binding.submitButton.setOnClickListener(::onSubmitButtonClick)
    }

    private fun onSubmitButtonClick(v: View) {
        if (binding.editTextQR.text.isNullOrEmpty())
            return

        updateQR()
    }

    private fun updateQR() {
        val text = binding.editTextQR.text.toString()
        val drawable = QrService.generate(requireActivity(), text)
        binding.imageQR.setImageDrawable(drawable)
    }
}