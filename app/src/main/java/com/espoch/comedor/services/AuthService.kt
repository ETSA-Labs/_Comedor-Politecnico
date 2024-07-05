package com.espoch.comedor.services

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.R
import com.espoch.comedor.data.DisplayNameResponse
import com.espoch.comedor.models.AppUser
import com.microsoft.identity.client.AcquireTokenSilentParameters
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.IPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.exception.MsalException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class provides methods to handle authentication with Microsoft Azure AD.
 * It supports initialization, sign-in, and managing result listeners for authentication events.
 */
class AuthService {
    companion object {
        private val listeners = mutableListOf<ResultListener>()
        private var singleApp: ISingleAccountPublicClientApplication? = null
        private var publicApp: IPublicClientApplication? = null
        @SuppressLint("StaticFieldLeak")
        private var context: FragmentActivity? = null

        var isSignedIn: Boolean = false

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
                        singleApp = application

                        CoroutineScope(Dispatchers.IO).launch {
                            // ...
                            val account = singleApp?.currentAccount?.currentAccount
                            isSignedIn = account != null

                            if (account != null) {
                                val accessToken = getToken()

                                /* ... */
                                AppUser.default.let {
                                    it.uid = account.id
                                    it.email = account.username
                                    it.idToken = account.idToken.toString()
                                }

                                if (!accessToken.isNullOrEmpty()) {
                                    AppUser.default.accessToken = accessToken
                                    getFullName(accessToken)
                                }
                            }
                            listeners.forEach { it.onCreate() }
                        }
                    }

                    /**
                     * Called when an error occurs during the creation of the PublicClientApplication.
                     *
                     * @param exception The exception thrown during the creation process.
                     */
                    override fun onError(exception: MsalException) {
                        listeners.forEach { it.onError("${exception.message} - line: 79") }
                    }
                }
            )
        }

        /**
         * Initiates the sign-in process.
         * Throws an exception if the service has not been initialized.
         */
        fun signIn() {
            singleApp?.signIn(
                context!!,
                null,
                arrayOf("User.Read"),
                object : AuthenticationCallback {
                    /**
                     * Called when the sign-in process completes successfully.
                     *
                     * @param authenticationResult The result of the authentication.
                     */
                    override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val account = authenticationResult?.account

                            if (account != null) {
                                val token = getToken()

                                /* ... */
                                AppUser.default.let {
                                    it.uid = account.id
                                    it.email = account.username
                                    it.idToken = account.idToken.toString()
                                }

                                if (!token.isNullOrEmpty()) {
                                    AppUser.default.accessToken = token
                                    getFullName(token)
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    listeners.forEach { it.onError("account is null") }
                                }
                            }
                        }
                    }

                    /**
                     * Called when an error occurs during the sign-in process.
                     *
                     * @param exception The exception thrown during the sign-in process.
                     */
                    override fun onError(exception: MsalException?) {
                        listeners.forEach { it.onError("${exception?.message.toString()} - line: 110") }
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
            singleApp?.signOut(
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
                        listeners.forEach { it.onError("${exception.message} - line: 143") }
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

        /**
         *
         */
        private fun getToken(): String? {
            val account = singleApp?.currentAccount?.currentAccount ?: return null

            val params = AcquireTokenSilentParameters.Builder()
                .fromAuthority(singleApp?.configuration?.defaultAuthority?.authorityURL.toString())
                .withScopes(listOf("User.Read"))
                .forAccount(account)
                .build()

            return singleApp?.acquireTokenSilent(params)?.accessToken
        }

        /**
         * Makes a network call to the Microsoft Graph API to retrieve the groups the authenticated user is a member of.
         *
         * @param accessToken The OAuth 2.0 access token used for authorization in the format "Bearer {token}".
         */
        private fun getFullName(accessToken: String) {
            // Initialize the Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl("https://graph.microsoft.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Create an implementation of the API endpoints defined by the GraphService interface
            val api: GraphService = retrofit.create(GraphService::class.java)

            // Make the network request to the API
            api.getDisplayName("Bearer $accessToken").enqueue(
                object: Callback<DisplayNameResponse> {
                    /**
                     * Called when a response is received from the server.
                     *
                     * @param call The original call that was made.
                     * @param response The response received from the server.
                     */
                    override fun onResponse(
                        call: Call<DisplayNameResponse>,
                        response: Response<DisplayNameResponse>,
                    ) {
                        if (response.isSuccessful) {
                            // Successfully received a response, extract and log the user info
                            val info = response.body()?.value

                            /* ... */
                            AppUser.default.displayName = info.toString()

                            listeners.forEach { it.onSignIn() }
                        } else {
                            // The server returned an error response
                            listeners.forEach { it.onError("${response.errorBody().toString()} - line: 218") }
                        }
                    }

                    /**
                     * Called when the network request fails.
                     *
                     * @param call The original call that was made.
                     * @param t The error that occurred during the network request.
                     */
                    override fun onFailure(call: Call<DisplayNameResponse>, t: Throwable) {
                        listeners.forEach { it.onError("${t.message} - line: 229") }
                    }
                }
            )
        }
    }

    /**
     * An class that defines the callback methods for handling the authentication process.
     */
    open class ResultListener {

        /**
         * Called when the authentication process is initiated.
         * This method should contain logic that needs to be executed at the start of the authentication process.
         */
        open fun onCreate() {}

        /**
         * Called when the authentication process completes successfully.
         * This method should contain logic that needs to be executed when authentication is successful.
         */
        open fun onSignIn() {}

        /**
         * Called when the sign-out process completes successfully.
         * This method should contain logic that needs to be executed when sign-out is successful.
         */
        open fun onSignOut() {}

        /**
         * Called when the authentication process is cancelled by the user.
         * This method should contain logic that needs to be executed when authentication is cancelled.
         */
        open fun onCancel() {}

        /**
         * Called when an error occurs during the authentication process.
         * This method should contain logic that needs to be executed in the event of an authentication error.
         *
         * @param msg The exception thrown during the authentication process.
         */
        open fun onError(msg: String?) {
            Log.d("MSAL", msg.toString())
        }
    }
}