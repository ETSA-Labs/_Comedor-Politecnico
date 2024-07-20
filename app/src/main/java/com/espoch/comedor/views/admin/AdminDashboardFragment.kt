package com.espoch.comedor.views.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.databinding.FragmentAdminDashboardBinding

class AdminDashboardFragment : Fragment() {

    private lateinit var binding: FragmentAdminDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnManageMenu.setOnClickListener(::onManageMenuButtonClick)
        binding.btnScanQR.setOnClickListener(::onScanQRButtonClick)
        binding.btnManageBookings.setOnClickListener(::onManageBookingsButtonClick)
        binding.btnManageRoles.setOnClickListener(::onManageRolesButtonClick)
    }

    private fun onManageMenuButtonClick(v: View) {
        //NavigationService.navigate("", )
    }

    private fun onScanQRButtonClick(v: View) {

    }

    private fun onManageBookingsButtonClick(v: View) {

    }

    private fun onManageRolesButtonClick(v: View) {

    }
}