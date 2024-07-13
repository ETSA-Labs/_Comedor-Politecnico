package com.espoch.comedor.admin.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.admin.databinding.FragmentMenuManagementBinding

class MenuManagementFragment : Fragment() {

    private lateinit var binding: FragmentMenuManagementBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuManagementBinding.inflate(inflater, container, false)
        return binding.root
    }
}