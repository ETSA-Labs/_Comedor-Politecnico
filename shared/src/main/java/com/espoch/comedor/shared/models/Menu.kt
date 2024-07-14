package com.espoch.comedor.shared.models

import com.espoch.comedor.shared.models.base.Observable

class Menu : Observable() {
    /*Unique identifier for the menu*/
    var menuId: String by property("")
    /*Date of the menu*/
    var date: String by property("")
    /*Name of the menu*/
    var nameDish: String by property("")
    /*Description of the menu*/
    var description: String by property("")
    /*Price of the menu*/
    var price: Float by property(0f)
    /*Availability of the menu*/
    var disponibility: Boolean by property(false)
    /*Image of the menu*/
    var image: String by property("")
}
