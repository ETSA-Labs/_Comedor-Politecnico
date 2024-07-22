package com.espoch.comedor.models

data class MenuItem(
    val menu_id: String = "",
    val fecha: Long = 0,
    val nombre_plato: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val disponibilidad: Boolean = true,
    val imagen: String = "",
    val categoria: String = ""
)
