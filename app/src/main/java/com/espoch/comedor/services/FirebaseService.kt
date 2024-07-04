package com.espoch.comedor.services

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.models.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseService {
    companion object {

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

        /**
         * Sign in as Guest in Firebase.
         */
        fun signIn(activity: FragmentActivity) {
            FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener {
                    Toast.makeText(activity, "Welcome to Firebase!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Something went wrong :(", Toast.LENGTH_SHORT).show()
                }
        }

        fun getAdmins(): List<AppUser> {
            val users = mutableListOf<AppUser>()

            Firebase.firestore
                .collection("admins")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val user = document.toObject(AppUser::class.java)
                        users.add(user)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", exception.message.toString())
                }

            return users
        }

        fun insertAdmin() {
            Firebase.firestore
                .collection("admins")
                .document(AppUser.default.uid)
                .set(AppUser)
                .addOnFailureListener { exception ->
                    Log.d("Firebase", exception.message.toString())
                }
        }
    }
}