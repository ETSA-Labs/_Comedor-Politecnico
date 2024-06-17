package com.espoch.comedor.services

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class FirebaseService {
    companion object {
        fun signIn(activity: FragmentActivity, idToken: String) {
            //val credential = MicrosoftAuthProvider.getCredential(idToken)
            FirebaseApp.initializeApp(activity)

            FirebaseAuth.getInstance().signInWithCustomToken(idToken)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        //val user = FirebaseAuth.getInstance().currentUser
                        Log.d("Firebase","Success")
                    } else {
                        Log.d("Firebase","Error")
                    }
                }
        }
    }
}