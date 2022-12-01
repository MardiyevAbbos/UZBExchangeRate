package com.example.uzbexchangerate.utils.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph


/**
 *  Safely navigate via the given Action
 *
 *  A fix for [Navigation action cannot be found in the current destination] crash
 * @param resActionId action that describes this navigation operation
 *
 * @author @BoyWonder
 */
fun NavController.navigateSafe(@IdRes resActionId: Int, hasPopUp: Boolean = false) {
    val destinationId = currentDestination?.getAction(resActionId)?.destinationId
    currentDestination?.let { node ->
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != null) {
            currentNode?.findNode(destinationId)
                ?.let { navigate(resActionId, null) }
        }
    }
}

/**
 *  Safely navigate via the given Action
 *
 *  A fix for [Navigation action cannot be found in the current destination] crash
 * @param resActionId action that describes this navigation operation
 * @param args arguments to pass to the destination
 *
 * @author @BoyWonder
 */
fun NavController.navigateSafe(@IdRes resActionId: Int, args: Bundle?, hasPopUp: Boolean = false) {
    val destinationId = currentDestination?.getAction(resActionId)?.destinationId
    currentDestination?.let { node ->
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != null) {
            currentNode?.findNode(destinationId)
                ?.let { navigate(resActionId, args) }
        }
    }
}


