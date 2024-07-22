package com.espoch.comedor.models

import com.google.firebase.firestore.Exclude

/**
 * Represents an user within the app
 */
class AppUser private constructor() {
    companion object {
        private var _current: AppUser? = null

        /**
         * Gets the current app user.
         */
        val current: AppUser
            get() {
                if (AppUser.Companion._current == null)
                    AppUser.Companion._current =
                        AppUser()

                return AppUser.Companion._current!!
            }

        /**
         * User has regular access.
         */
        const val CUSTOMER: Int = 0

        /**
         * User has full access.
         */
        const val ADMIN: Int = 1
    }


    /**
     * Unique identifier for the user.
     */
    //@get:Exclude
    var uid: String = ""

    /**
     * Short name of the user.
     */
    @get:Exclude
    var shortName: String = ""

    /**
     * Nickname of the user.
     */
    @get:Exclude
    var nickName: String = ""

    /**
     * Email address of the user.
     */
    var email: String = ""

    /**
     * Full name of the user.
     */
    var fullName: String = ""

    /**
     * Role of the user, either CUSTOMER or ADMIN.
     */
    var role: Int = 0

    /**
     * Faculty of the user.
     */
    var faculty: String = ""

    /**
     * Career of the user.
     */
    var career: String = ""

}