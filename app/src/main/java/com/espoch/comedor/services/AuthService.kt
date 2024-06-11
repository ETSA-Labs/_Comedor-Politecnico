package com.espoch.comedor.services

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.R

class AuthService {
    companion object {

        private var prompt: BiometricPrompt? = null
        private var info: BiometricPrompt.PromptInfo? = null

        fun authenticate(activity: FragmentActivity) {
            if (prompt == null) prompt = BiometricPrompt(
                activity,
                activity.mainExecutor,
                AuthCallback()
            )

            if (info == null) info = BiometricPrompt.PromptInfo.Builder()
                .setTitle(activity.getString(R.string.title_unlock))
                .setSubtitle(activity.getString(R.string.body_unlock))
                .setAllowedAuthenticators(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()

            prompt!!.authenticate(info!!)
        }

        class AuthCallback : BiometricPrompt.AuthenticationCallback()
    }
}