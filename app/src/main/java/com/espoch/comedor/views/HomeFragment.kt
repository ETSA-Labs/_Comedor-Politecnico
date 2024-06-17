package com.espoch.comedor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.R
import com.espoch.comedor.databinding.FragmentHomeBinding
import com.espoch.comedor.extensions.isLightStatusBar

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        activity.isLightStatusBar = false

        binding.radioGroupCategories.let {
            it.setOnCheckedChangeListener(::onCategoriesGroupCheckedChange)
            it.check(R.id.radio_button_breakfast)
        }
    }

    private fun onCategoriesGroupCheckedChange(group: RadioGroup, id: Int) {

    }
}