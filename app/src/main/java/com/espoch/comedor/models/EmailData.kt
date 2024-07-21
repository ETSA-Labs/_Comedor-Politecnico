package com.espoch.comedor.models

import java.util.Observable
import kotlin.properties.Delegates

class EmailData private constructor() : Observable() {
    companion object {
        private var _default: EmailData? = null

        val default: EmailData
            get() {
                if (_default == null)
                    _default = EmailData()
                return _default!!
            }
    }

    var sender: Sender by Delegates.observable(Sender("", "")) { _, _, _ -> setChanged(); notifyObservers() }
    var to: List<Recipient> by Delegates.observable(emptyList()) { _, _, _ -> setChanged(); notifyObservers() }
    var subject: String by Delegates.observable("") { _, _, _ -> setChanged(); notifyObservers() }
    var htmlContent: String by Delegates.observable("") { _, _, _ -> setChanged(); notifyObservers() }

    data class Sender(
        val name: String,
        val email: String
    )

    data class Recipient(
        val email: String,
        val name: String
    )

    data class EmailResponse(
        val messageId: String
    )
}
