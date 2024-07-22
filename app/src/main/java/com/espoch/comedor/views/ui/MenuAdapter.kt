package com.espoch.comedor.views.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.espoch.comedor.databinding.ItemMenuBinding
import com.espoch.comedor.models.MenuItem

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val menuItems = mutableListOf<MenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    fun submitList(items: List<MenuItem>) {
        menuItems.clear()
        menuItems.addAll(items)
        notifyDataSetChanged()
    }

    class MenuViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuItem) {
            binding.menuName.text = menuItem.nombre_plato
            binding.menuDescription.text = menuItem.descripcion
            binding.menuPrice.text = menuItem.precio
            Glide.with(binding.root.context)
                .load(menuItem.imagen)
                .into(binding.menuImage)
        }
    }
}
