/*
package com.espoch.comedor.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.espoch.comedor.R

class ReservationAdapterFragment(private val reservations : List<Reservation>) :
    RecyclerView.Adapter<ReservationAdapterFragment.ViewHolder>(){

        class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val ivMeal : ImageView = view.findViewById(R.id.ivMeal)
            val tvMealType : TextView = view.findViewById(R.id.tvMealType)
            val tvDate : TextView = view.findViewById(R.id.tvDate)
            val tvTime : TextView = view.findViewById(R.id.tvTime)
            val tvOrderCount : TextView = view.findViewById(R.id.tvOrderCount)
            val btnQR : Button = view.findViewById(R.id.btnQR)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_reservation, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.tvMealType.text = reservation.mealType
        holder.tvDate.text = reservation.date
        holder.tvTime.text = reservation.time
        holder.tvOrderCount.text = "${reservation.orderCount} pedido"

        //Establecer la imagen de la comida en función del tipo de comida

        when (reservation.mealType) {
            "Desayuno" -> holder.ivMeal.setImageResource(R.drawable.im_menupoli)
            "Almuerzo" -> holder.ivMeal.setImageResource(R.drawable.im_menupoli)
            else -> holder.ivMeal.setImageResource(R.drawable.im_menupoli)
        }

        //Establecer el listener del botón QR
        holder.btnQR.setOnClickListener {
            // Implement QR code generation or display
        }
    }
    override fun getItemCount() = reservations.size

}*/
