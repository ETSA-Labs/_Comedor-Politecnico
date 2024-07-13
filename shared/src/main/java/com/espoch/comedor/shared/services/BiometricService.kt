package com.espoch.comedor.shared.services

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.shared.R

/**
 * This class provides methods to handle biometric authentication.
 */
class BiometricService {
    companion object {
        private val listeners = mutableListOf<ResultListener>()
        private var info: BiometricPrompt.PromptInfo? = null
        private var prompt: BiometricPrompt? = null

        /**
         * Authenticates the user using biometric authentication.
         *
         * @param activity The FragmentActivity requesting authentication.
         */
        fun authenticate(activity: FragmentActivity) {
            // Create a BiometricPrompt instance if it is null
            if (prompt == null) {
                prompt = BiometricPrompt(
                    activity,
                    activity.mainExecutor,
                    object: BiometricPrompt.AuthenticationCallback() {
                        /**
                         * Callback method invoked when biometric authentication succeeds.
                         *
                         * @param result The result of the biometric authentication.
                         */
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            listeners.forEach { it.onSucceeded() }
                        }

                        /**
                         * Callback method invoked when biometric authentication fails.
                         */
                        override fun onAuthenticationFailed() {
                            listeners.forEach { it.onFailed() }
                        }

                        /**
                         * Callback method invoked when an error occurs during biometric authentication.
                         *
                         * @param errorCode The error code.
                         * @param errString The error message.
                         */
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            listeners.forEach { it.onError(errString) }
                        }
                    }
                )
            }

            // Build the authentication prompt info if it is null
            if (info == null) {
                info = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(activity.getString(R.string.title_unlock))
                    .setSubtitle(activity.getString(R.string.body_unlock))
                    .setAllowedAuthenticators(
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                    .build()
            }

            // Start the authentication process using the BiometricPrompt and PromptInfo
            prompt!!.authenticate(info!!)
        }

        /**
         * Adds a ResultListener to the list of listeners to be notified of authentication results.
         *
         * @param l The ResultListener to be added.
         */
        fun addResultListener(l: ResultListener) {
            listeners.add(l)
        }

        /**
         * Removes a ResultListener from the list of listeners.
         *
         * @param l The ResultListener to be removed.
         */
        fun removeResultListener(l: ResultListener) {
            listeners.remove(l)
        }
    }

    /**
     * A nested interface containing callback methods for biometric authentication results.
     */
    interface ResultListener {
        /**
         * Called when biometric authentication succeeds.
         */
        fun onSucceeded()

        /**
         * Called when biometric authentication fails.
         */
        fun onFailed()

        /**
         * Called when an error occurs during biometric authentication.
         *
         * @param msg The error message.
         */
        fun onError(msg: CharSequence)
    }
}