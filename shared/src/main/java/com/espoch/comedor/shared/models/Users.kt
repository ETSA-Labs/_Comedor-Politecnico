package com.espoch.comedor.shared.models

import com.espoch.comedor.shared.models.base.Observable

class Users : Observable() {
    /*
    * unique identifier for the user*/
    var userId: String by property("")
    /*
    * name of the user*/
    var name: String by property("")
    /*
    * last name of the user*/
    var lastName: String by property("")
    /*
    * email of the user*/
    var email: String by property("")
    /*
    * password of the user*/
    var password: String by property("")
    /*
    * role of the user*/
    var roleId: String by property("")
    /*
    * date of registration of the user*/
    var dateregister: String by property("")
    /*
    * department of the user*/
    var departmentId: String by property("")
}