package com.espoch.comedor.models

/**
 * Represents an user within the app
 */
class AppUser() : Observable() {
    companion object {
        private var _default: AppUser? = null

        /**
         * Gets the singleton object class.
         */
        val default: AppUser
            get() {
                if (_default == null)
                    _default = AppUser()

                return _default!!
            }
    }

    /**
     * Unique identifier for the user.
     */
    @get:com.google.firebase.firestore.Exclude
    @get:com.google.firebase.database.Exclude
    var uid: String by property("")

    /**
     * Short name of the user.
     */
    @get:com.google.firebase.firestore.Exclude
    @get:com.google.firebase.database.Exclude
    var shortName: String by property("")

    /**
     * Full name of the user.
     */
    var displayName: String by property("")

    /**
     * Nickname of the user.
     */
    @get:com.google.firebase.firestore.Exclude
    @get:com.google.firebase.database.Exclude
    var nickName: String by property("")

    /**
     * Email address of the user.
     */
    @get:com.google.firebase.firestore.Exclude
    @get:com.google.firebase.database.Exclude
    var email: String by property("")

    /**
     * Auth token of the user.
     */
    @get:com.google.firebase.firestore.Exclude
    @get:com.google.firebase.database.Exclude
    var accessToken: String by property("")

    /**
     *
     */
    @get:com.google.firebase.firestore.Exclude
    @get:com.google.firebase.database.Exclude
    var idToken: String by property("")

    /**
     * Role of the user, either CUSTOMER or ADMIN.
     */
    var role: UserRole by property(UserRole.CUSTOMER)

    /**
     * Enum representing the user roles.
     */
    enum class UserRole {
        CUSTOMER, ADMIN
    }
}