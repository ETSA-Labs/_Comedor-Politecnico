package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

class Ordermenu : Observable() {
    /*Unique identifier for the order*/
    var ordermenuId: String by property("")
    /*reservation od the menu*/
    var reservaId: String by property("")
    /*menu of the order*/
    var menuId: String by property("")
    /*quantity of the menu*/
    var quantity: Int by property(0)
}
