package com.espoch.comedor.services

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.models.AppUser
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class FirebaseService {
    companion object {
        private val listeners = mutableListOf<ResultListener>()

        var isSignedIn: Boolean = false

        /*
           Check this!! IDK why it doesn't work.

        fun signIn(activity: FragmentActivity, accessToken: String, idToken: String) {
            val credential = OAuthProvider.newCredentialBuilder("microsoft.com")
                .setAccessToken(accessToken)
                .setIdToken(idToken)
                .build()

            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        //val user = FirebaseAuth.getInstance().currentUser
                        Log.d("Firebase","Success")
                    } else {
                        Log.d("Firebase","Error: ${task.exception?.message.toString()}")
                    }
                }
        }
         */

        fun initialize(activity: FragmentActivity) {
            FirebaseApp.initializeApp(activity)
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
         * Sign in as Guest in Firebase.
         */
        fun signIn() {
            FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener {
                    isSignedIn = true
                    listeners.forEach { it.onSuccess() }
                }
                .addOnFailureListener {
                    listeners.forEach { it.onError("") }
                }
        }
    }

    open class ResultListener {

        /**
         * Called when the authentication process completes successfully.
         * This method should contain logic that needs to be executed when authentication is successful.
         */
        open fun onSuccess() {}

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

    open class FirestoreResult<T> {
        open fun onComplete(value: T?) {}

        open fun onFailure(exception: Exception) {}
    }

    /**
     *
     */
    class Users {
        companion object {

            /**
             *
             */
            fun get(uid: String, callback: FirestoreResult<com.espoch.comedor.models.AppUser>? = null) {
                Firebase.firestore
                    .collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { result ->
                        val user = result.toObject(com.espoch.comedor.models.AppUser::class.java)

                        callback?.onComplete(user)
                    }
                    .addOnFailureListener {
                        callback?.onFailure(it)
                    }
            }

            /**
             *
             */
            fun add(user: com.espoch.comedor.models.AppUser, callback: FirestoreResult<Boolean>? = null) {
                Firebase.firestore
                    .collection("users")
                    .document(user.uid)
                    .set(user)
                    .addOnSuccessListener {
                        callback?.onComplete(true)
                    }
                    .addOnFailureListener {
                        callback?.onComplete(false)
                        callback?.onFailure(it)
                    }
            }
        }
    }
    fun saveUserToFirestore(userId: String, email: String, fullName: String, role: Int) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "email" to email,
            "fullName" to fullName,
            "role" to role
        )
        db.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                Log.d("Firebase", "User successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error writing user", e)
            }
    }
}