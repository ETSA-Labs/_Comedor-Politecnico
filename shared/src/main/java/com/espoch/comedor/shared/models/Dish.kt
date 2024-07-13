package com.espoch.comedor.shared.models

import com.espoch.comedor.shared.models.base.Observable

/**
 * Represents a dish with various attributes such as name, description, image source, price, rating, and favorite status.
 * The class extends Observable, allowing it to notify observers of changes.
 */
class Dish : Observable() {

    /**
     * Unique identifier for the dish.
     */
    var uid: String by property("")

    /**
     * Name of the dish.
     */
    var name: String by property("")

    /**
     * Brief description of the dish.
     */
    var description: String by property("")

    /**
     * Source URL or path of the dish's image.
     */
    var imgSource: String by property("")

    /**
     * Price of the dish.
     */
    var price: Float by property(0f)

    /**
     * Rating of the dish, typically a float value between 0 and 5.
     */
    var rating: Float by property(0f)

    /**
     * Indicates whether the dish is marked as a favorite.
     */
    var favourite: Boolean by property(false)
}
