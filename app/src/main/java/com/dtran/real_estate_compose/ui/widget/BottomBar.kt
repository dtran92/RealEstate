package com.dtran.real_estate_compose.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.dtran.real_estate_compose.R
import com.dtran.real_estate_compose.navigation.MainRoute
import com.dtran.real_estate_compose.navigation.Screen

@Composable
fun AppBottomBar(
    navController: NavController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer, modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        val currentMainRoute = navController.currentBottomBarItemAsState()
        BottomBarItem.entries.forEach { screen ->
            val isTabSelected = screen == currentMainRoute.value
            NavigationBarItem(isTabSelected, onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = false
                        saveState = false
                    }
                    restoreState = false
                    launchSingleTop = true
                }
            }, icon = {
                Icon(
                    ImageVector.vectorResource(id = screen.iconId),
                    contentDescription = null,
                )
            },
                label = { Text(text = stringResource(id = screen.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                    unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                    indicatorColor = Color.Transparent
                )
            )
        }

    }
}

enum class BottomBarItem(val route: MainRoute, val iconId: Int, @StringRes val label: Int) {
    Home(MainRoute.HomeRoute, R.drawable.ic_home, R.string.label_home),
    Search(MainRoute.SearchRoute, R.drawable.ic_search, R.string.label_search)
}


@Composable
private fun NavController.currentBottomBarItemAsState(): State<BottomBarItem> {
    val selectedItem = remember { mutableStateOf(BottomBarItem.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.SearchScreen.javaClass.canonicalName } -> {
                    selectedItem.value = BottomBarItem.Search
                }

                destination.hierarchy.any { it.route == Screen.HomeScreen.javaClass.canonicalName } -> {
                    selectedItem.value = BottomBarItem.Home
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}
