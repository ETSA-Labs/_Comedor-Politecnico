package com.espoch.comedor.extensions

import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.uncheckItems() {
    val menu = this.menu

    menu.setGroupCheckable(0, true, false)

    for (i in 0..<menu.size())
        menu.getItem(i).setChecked(false)

    menu.setGroupCheckable(0, true, true)
}