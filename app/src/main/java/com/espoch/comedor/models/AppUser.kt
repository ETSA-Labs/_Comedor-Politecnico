package com.espoch.comedor.models

class AppUser : Observable() {
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
     * Role of the user, either CUSTOMER or ADMIN.
     */
    var role: UserRole by property(UserRole.CUSTOMER)

    /**
     * Enum representing the user roles.
     */
    enum class UserRole {
        CUSTOMER, ADMIN, GOD
    }
}