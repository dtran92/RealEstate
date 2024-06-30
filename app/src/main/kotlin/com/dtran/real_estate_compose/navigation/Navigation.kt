package com.dtran.real_estate_compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dtran.real_estate_compose.data.local.DataStoreUtil
import com.dtran.real_estate_compose.ui.screen.home.HomeScreen
import com.dtran.real_estate_compose.ui.screen.search.SearchScreen
import com.dtran.real_estate_compose.ui.screen.welcome.WelcomeScreen
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier = Modifier,
) {
    val datastore = koinInject<DataStoreUtil>()
    val firstLaunch = datastore.firstLaunch.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = if (firstLaunch.value == false) Screen.HomeScreen else Screen.WelcomeScreen,
        modifier = modifier
    ) {
        composable<Screen.WelcomeScreen> {
            WelcomeScreen {
                navController.navigate(MainRoute.HomeRoute)
            }
        }
        navigation<MainRoute.HomeRoute>(startDestination = Screen.HomeScreen) {
            composable<Screen.HomeScreen> {
                HomeScreen(
                    navController = navController
                )
            }
        }
        navigation<MainRoute.SearchRoute>(startDestination = Screen.SearchScreen) {
            composable<Screen.SearchScreen> {
                SearchScreen(navController = navController)
            }
        }

    }
}