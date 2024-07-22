package com.espoch.comedor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.espoch.comedor.databinding.FragmentLunchBinding
import com.espoch.comedor.adapters.MenuAdapter
import com.espoch.comedor.models.MenuItem
import com.google.firebase.firestore.FirebaseFirestore

class LunchFragment : Fragment() {

    private lateinit var binding: FragmentLunchBinding
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@LunchFragment.adapter
        }

        loadLunchMenu()
    }

    private fun loadLunchMenu() {
        firestore.collection("menu")
            .whereEqualTo("categoria", "Almuerzo")
            .get()
            .addOnSuccessListener { documents ->
                val menuItems = documents.map { document ->
                    document.toObject(MenuItem::class.java)
                }
                adapter.submitList(menuItems)
            }
            .addOnFailureListener {
                // Handle the error
            }
    }
}
