package com.espoch.comedor

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.espoch.comedor.databinding.ActivityLoginBinding
import com.espoch.comedor.models.AppUser
import com.espoch.comedor.services.AuthService

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val controller = WindowCompat.getInsetsController(window, window.decorView)

        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMicrosoft.setOnClickListener(::onMicrosoftButtonClick)

        AuthService.addResultListener(AuthResultCallback())

        onBackPressedDispatcher.addCallback(this, OnBackInvokedCallback())
    }

    private fun onMicrosoftButtonClick(v: View) {
        AuthService.signIn()
    }

    private inner class AuthResultCallback : AuthService.ResultListener() {
        override fun onSignIn() {
            super.onSignIn()

            Toast.makeText(this@LoginActivity, AppUser.default.fullName, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private inner class OnBackInvokedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }
}