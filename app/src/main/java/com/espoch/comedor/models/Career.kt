package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

class Career : Observable() {
    /*
    * unique identifier for the career*/
    var careerId: String by property("")
    /*
    * name of the career*/
    var careerName: String by property("")

}
