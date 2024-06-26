package com.espoch.comedor.services

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

class FirebaseService {
    companion object {
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
    }
}