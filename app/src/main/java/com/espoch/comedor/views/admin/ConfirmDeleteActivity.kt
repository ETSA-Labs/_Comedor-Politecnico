package com.espoch.comedor.views.admin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.espoch.comedor.R
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmDeleteActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_delete)

        val dishId = intent.getStringExtra("DISH_ID") ?: ""

        findViewById<Button>(R.id.btnConfirmDelete).setOnClickListener {
            deleteDish(dishId)
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            finish() // Close the activity
        }
    }

    private fun deleteDish(dishId: String) {
        firestore.collection("menu").document(dishId).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Dish deleted successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the activity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error deleting dish", Toast.LENGTH_SHORT).show()
            }
    }
}
