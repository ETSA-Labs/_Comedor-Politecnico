package com.espoch.comedor.shared.models

import com.espoch.comedor.shared.models.base.Observable

class Roles : Observable() {
    /*
    * unique identifier for the role*/
    var rolId: String by property("")
    /*
    * name of the role*/
    var nameRol: String by property("")
}