package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

class Roles : Observable() {
    /*
    * unique identifier for the role*/
    var rolId: String by property("")
    /*
    * name of the role*/
    var nameRol: String by property("")
}