package com.espoch.comedor.models

import com.espoch.comedor.models.base.Observable

class Ticket : Observable() {
    /*
    *Unique identifier for the ticket*/
    var ticketId: String by property("")
    /*
    *Unique identifier for the user who created the ticket*/
    var userId: String by property("")
    /*
    *date of the ticket*/
    var dateTicket: String by property("")
    /*
    *status of the ticket*/
    var status: String by property("")
    /*
    ]time of withdrawal*/
    var timeWithdrawal: String by property("")
    /*
    *time of arrival*/
    var timeArrival: String by property("")
}
