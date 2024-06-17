package com.espoch.comedor.extensions

import kotlin.reflect.KProperty

internal fun KProperty<*>.name(): String {
    return this.name
}