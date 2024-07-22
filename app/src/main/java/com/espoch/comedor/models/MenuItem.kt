package com.espoch.comedor.models

data class MenuItem(
    val nombre_plato: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val imagen: String = "",
    val categoria: String = "",
    val disponibilidad: Boolean = true,
    val fecha: Long = 0,
    val menu_id: String = ""
)
