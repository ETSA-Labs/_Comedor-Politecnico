package com.espoch.comedor.shared.models

import com.espoch.comedor.shared.models.base.Observable

class Career : Observable() {
    /*
    * unique identifier for the career*/
    var careerId: String by property("")
    /*
    * name of the career*/
    var careerName: String by property("")

}
