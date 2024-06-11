package com.espoch.comedor.services

import androidx.annotation.IdRes
import androidx.navigation.NavController

/**
 * Service for managing multiple `NavController` instances by name.
 * This service allows navigation operations such as registering, unregistering,
 * navigating, going back, and resetting navigation stacks.
 */
class NavigationService {
    companion object {

        private val controllers = mutableMapOf<String, NavController>()

        /**
         * Registers a `NavController` with a specific name.
         * This method stores the `NavController` in a map with a given name as the key.
         *
         * @param name The name to identify the `NavController`.
         * @param nav The `NavController` to be registered.
         */
        fun register(name: String, nav: NavController) {
            controllers[name] = nav
        }

        /**
         * Unregisters a `NavController` by name.
         * This method removes the `NavController` associated with the specified name from the map.
         *
         * @param name The name of the `NavController` to be unregistered.
         */
        private fun unregister(name: String) {
            controllers.remove(name)
        }

        /**
         * Navigates to a specific destination in the `NavController` registered with the specified name.
         *
         * @param name The name of the `NavController`.
         * @param res The resource ID of the navigation destination.
         */
        fun navigate(name: String, @IdRes res: Int) {
            if (controllers.containsKey(name))
                controllers[name]?.navigate(res)
        }

        /**
         * Pops the back stack in the `NavController` registered with the specified name.
         * This effectively navigates back to the previous state in the navigation stack.
         *
         * @param name The name of the `NavController`.
         * @return `true` if the back stack was successfully popped; `false` if no `NavController` with the specified name is found.
         */
        fun goBack(name: String): Boolean {
            if (!controllers.containsKey(name))
                return false

            return controllers[name]?.popBackStack() ?: false
        }

        /**
         * Resets the navigation stack in the `NavController` registered with the specified name.
         * This pops all backstack elements up to the start destination, without popping the start destination itself.
         *
         * @param name The name of the `NavController`.
         * @return `true` if the reset was successful; `false` if no `NavController` with the specified name is found.
         */
        fun reset(name: String): Boolean {
            if (!controllers.containsKey(name))
                return false

            return controllers[name]?.graph?.startDestinationId?.let {
                controllers[name]?.popBackStack(
                    it,
                    false
                )
            }!!
        }
    }
}