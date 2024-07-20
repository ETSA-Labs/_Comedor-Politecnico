package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

/**
 *
 */
class Faculty : Observable() {
    /**
     *unique for the faculty
     */
    var id: Int = 0

    /**
     *name of the faculty
     */
    var name: String = ""
    /*
    * career of the faculty*/
    var careerId: String = ""
}