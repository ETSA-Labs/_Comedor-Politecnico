package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

class Departament : Observable() {
    /*
    * unique identifier for the department*/
    var departamentId: String by property("")
    /*
    * name of the department*/
    var nameDepartament: String by property("")
    /*
    * unique identifier for the faculty*/
    var facultyId: String by property("")
}
