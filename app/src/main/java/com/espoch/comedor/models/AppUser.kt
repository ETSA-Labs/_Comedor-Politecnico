package com.espoch.comedor.models

/**
 * Represents an user within the app
 */
class AppUser private constructor() : Observable() {
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
    var uid: String by property("")

    /**
     * Short name of the user.
     */
    var shortName: String by property("")

    /**
     * Full name of the user.
     */
    var fullName: String by property("")

    /**
     * Nickname of the user.
     */
    var nickName: String by property("")

    /**
     * Email address of the user.
     */
    var email: String by property("")

    /**
     * Auth token of the user.
     */
    var token: String by property("")

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
