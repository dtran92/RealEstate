package com.dtran.real_estate_compose.util

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun <T : Any> NavController.navigateSafely(route: T, builder: NavOptionsBuilder.() -> Unit) {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route) { builder() }
    }
}