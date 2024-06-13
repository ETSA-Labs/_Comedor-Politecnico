package com.espoch.comedor.services

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.espoch.comedor.R
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.IPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.SilentAuthenticationCallback
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This class provides methods to handle authentication with Microsoft Azure AD.
 * It supports initialization, sign-in, and managing result listeners for authentication events.
 */
class AuthService {
    companion object {
        private val listeners = mutableListOf<ResultListener>()
        private var appClient: ISingleAccountPublicClientApplication? = null
        @SuppressLint("StaticFieldLeak")
        private var context: FragmentActivity? = null
        private val scopes = arrayOf("User.Read")

        /**
         * Initializes the authentication service.
         *
         * @param activity The FragmentActivity that initializes the authentication service.
         */
        fun initialize(activity: FragmentActivity) {
            context = activity
            PublicClientApplication.createSingleAccountPublicClientApplication(
                context!!,
                R.raw.msal_config,
                object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                    /**
                     * Called when the PublicClientApplication is successfully created.
                     *
                     * @param application The created ISingleAccountPublicClientApplication instance.
                     */
                    override fun onCreated(application: ISingleAccountPublicClientApplication?) {
                        appClient = application
                        CoroutineScope(Dispatchers.IO).launch {
                            /*
                            val account = appClient?.acquireTokenSilentAsync()

                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, account?.username, Toast.LENGTH_SHORT).show()
                                Log.d("MSAL", account?.authority.toString())
                            }
                             */
                        }
                        listeners.forEach { it.onCreate() }
                    }

                    /**
                     * Called when an error occurs during the creation of the PublicClientApplication.
                     *
                     * @param exception The exception thrown during the creation process.
                     */
                    override fun onError(exception: MsalException) {
                        listeners.forEach { it.onError(exception) }
                    }
                }
            )
        }

        /**
         * Initiates the sign-in process.
         * Throws an exception if the service has not been initialized.
         */
        fun signIn() {
            appClient?.signIn(
                context!!,
                null,
                scopes,
                object : AuthenticationCallback {
                    /**
                     * Called when the sign-in process completes successfully.
                     *
                     * @param authenticationResult The result of the authentication.
                     */
                    override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                        listeners.forEach { it.onSuccess(authenticationResult) }
                    }

                    /**
                     * Called when an error occurs during the sign-in process.
                     *
                     * @param exception The exception thrown during the sign-in process.
                     */
                    override fun onError(exception: MsalException?) {
                        listeners.forEach { it.onError(exception) }
                    }

                    /**
                     * Called when the sign-in process is cancelled by the user.
                     */
                    override fun onCancel() {
                        listeners.forEach { it.onCancel() }
                    }
                }
            )
        }

        /**
         * Initiates the sign-out process.
         * Notifies listeners when sign-out is successful or if an error occurs.
         */
        fun signOut() {
            appClient?.signOut(
                object: ISingleAccountPublicClientApplication.SignOutCallback {
                    /**
                     * Called when the sign-out process completes successfully.
                     */
                    override fun onSignOut() {
                        listeners.forEach { it.onSignOut() }
                    }

                    /**
                     * Called when an error occurs during the sign-out process.
                     *
                     * @param exception The exception thrown during the sign-out process.
                     */
                    override fun onError(exception: MsalException) {
                        listeners.forEach { it.onError(exception) }
                    }
                }
            )
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
     * An interface that defines the callback methods for handling the authentication process.
     */
    interface ResultListener {

        /**
         * Called when the authentication process is initiated.
         * This method should contain logic that needs to be executed at the start of the authentication process.
         */
        fun onCreate()

        /**
         * Called when the authentication process completes successfully.
         * This method should contain logic that needs to be executed when authentication is successful.
         *
         * @param result The result of the authentication.
         */
        fun onSuccess(result: IAuthenticationResult?)

        /**
         * Called when the sign-out process completes successfully.
         * This method should contain logic that needs to be executed when sign-out is successful.
         */
        fun onSignOut()

        /**
         * Called when the authentication process is cancelled by the user.
         * This method should contain logic that needs to be executed when authentication is cancelled.
         */
        fun onCancel()

        /**
         * Called when an error occurs during the authentication process.
         * This method should contain logic that needs to be executed in the event of an authentication error.
         *
         * @param exception The exception thrown during the authentication process.
         */
        fun onError(exception: MsalException?)
    }
}