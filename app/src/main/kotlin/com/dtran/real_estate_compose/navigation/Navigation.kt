package com.dtran.real_estate_compose.navigation

import android.annotation.SuppressLint
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
import com.dtran.real_estate_compose.util.navigateSafely
import org.koin.compose.koinInject

@SuppressLint("RestrictedApi")
@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier = Modifier,
) {
    val datastore = koinInject<DataStoreUtil>()
    val firstLaunch = datastore.firstLaunch.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = if (firstLaunch.value == false) MainRoute.HomeRoute else Screen.WelcomeScreen
    ) {
        composable<Screen.WelcomeScreen> {
            WelcomeScreen {
                navController.navigate(MainRoute.HomeRoute)
            }
        }
        navigation(route = MainRoute.HomeRoute::class, startDestination = Screen.HomeScreen::class) {
            composable<Screen.HomeScreen> {
                HomeScreen(
                    navController = navController
                )
            }
        }
        navigation(route = MainRoute.SearchRoute::class, startDestination = Screen.SearchScreen::class) {
            composable<Screen.SearchScreen> {
                SearchScreen(navController = navController)
            }
        }
    }
}