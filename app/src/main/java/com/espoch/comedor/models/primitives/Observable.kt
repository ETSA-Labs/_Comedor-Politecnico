package com.espoch.comedor.models.primitives

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Abstract base class that allows subclasses to observe changes in their properties.
 */
abstract class Observable {
    // List of listeners that will be notified when properties change
    private val listeners = mutableListOf<PropertyChangedListener>()

    /**
     * Adds a listener to the list of listeners.
     *
     * @param listener The listener to be added.
     */
    fun addPropertyChangedListener(listener: PropertyChangedListener) {
        listeners.add(listener)
    }

    /**
     * Removes a listener from the list of listeners.
     *
     * @param listener The listener to be removed.
     */
    fun removePropertyChangedListener(listener: PropertyChangedListener) {
        listeners.remove(listener)
    }

    /**
     * Notifies all listeners that a property is about to change.
     *
     * @param propertyName The name of the property that is changing.
     */
    private fun notifyPropertyChanging(propertyName: String) {
        listeners.forEach { it.propertyChanging(propertyName) }
    }

    /**
     * Notifies all listeners that a property has changed.
     *
     * @param propertyName The name of the property that has changed.
     */
    private fun notifyPropertyChanged(propertyName: String) {
        listeners.forEach { it.propertyChanged(propertyName) }
    }

    /**
     * Creates an observable property delegate that notifies listeners of property changes.
     *
     * @param value The initial value of the property.
     * @param onChange A callback function that is called when the property changes.
     * @return A property delegate that handles property changes.
     */
    protected fun <T> property(value: T, onChange: (KProperty<*>, T, T) -> Unit = { _, _, _ -> }): ReadWriteProperty<Any?, T> {
        return Delegates.observable(value) { property, oldValue, newValue ->
            if (oldValue != newValue) {
                notifyPropertyChanging(property.name)
                onChange(property, oldValue, newValue)
                notifyPropertyChanged(property.name)
            }
        }
    }

    /**
     * Interface for listeners that respond to property change events.
     */
    interface PropertyChangedListener {
        /**
         * Called before a property changes.
         *
         * @param propertyName The name of the property that is changing.
         */
        fun propertyChanging(propertyName: String)

        /**
         * Called after a property has changed.
         *
         * @param propertyName The name of the property that has changed.
         */
        fun propertyChanged(propertyName: String)
    }
}