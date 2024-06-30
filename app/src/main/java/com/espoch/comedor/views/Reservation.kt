package com.espoch.comedor.views

data class Reservation(
    val mealType: String,
    val date: String,
    val time: String,
    val orderCount: Int
)