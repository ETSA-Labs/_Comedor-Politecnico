package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

class Transaction : Observable() {
    /*
    * unique for the transaction*/
    var transactionId: String by property("")
    /*unique for the reservation*/
    var orderId: String by property("")
    /*
    * date of the transaction*/
    var transactionDate: String by property("")
    /*
    * amount of the transaction*/
    var amount: Float by property(0f)
    /*
    * method of payment*/
    var paymentMethod: String by property("")
}