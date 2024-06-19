package com.dtran.real_estate_compose.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object WelcomeScreen : Screen()

    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object SearchScreen : Screen()
}

@Serializable
sealed class MainRoute {
    @Serializable
    data object HomeRoute : MainRoute()

    @Serializable
    data object SearchRoute : MainRoute()
}