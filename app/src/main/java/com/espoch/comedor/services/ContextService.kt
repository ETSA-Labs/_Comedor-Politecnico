package com.espoch.comedor.services

import android.content.Context

class ContextService {
    companion object {

        private val contexts = mutableMapOf<String, Context>()

        fun register(name: String, context: Context) {
            contexts[name] = context
        }

        fun unregister(name: String) {
            contexts.remove(name)
        }

        fun get(name: String) : Context? {
            if (!contexts.containsKey(name))
                return null

            return contexts[name]
        }
    }
}