package myapp.catscatalogexpanded.ui.home

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import myapp.catscatalogexpanded.ui.breedsScreen.breedsNavigation.BreedNavigation
import myapp.catscatalogexpanded.ui.leaderBoardScreen.ui.LeaderboardScreen
import myapp.catscatalogexpanded.ui.leaderBoardScreen.ui.LeaderboardScreenViewModel
import myapp.catscatalogexpanded.ui.profileDetailsScreen.ProfileDetailsScreen
import myapp.catscatalogexpanded.ui.profileDetailsScreen.ProfileDetailsScreenViewModel
import myapp.catscatalogexpanded.ui.utilities.BottomNavItem

@Composable
fun HomeScreen(
    mainNavController: NavController
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Breeds,
        BottomNavItem.Leaderboard,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(items.first().route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(item.label) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Breeds.route,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
                    bottom = innerPadding.calculateBottomPadding()
                )

        ) {
            composable(BottomNavItem.Breeds.route) {
                BreedNavigation(mainNavController = mainNavController)
            }

            composable(BottomNavItem.Leaderboard.route) {
                val leaderboardScreenViewModel = hiltViewModel<LeaderboardScreenViewModel>()
                LeaderboardScreen(leaderboardScreenViewModel)
            }

            composable(BottomNavItem.Profile.route) {
                val profileDetailsScreenViewModel = hiltViewModel<ProfileDetailsScreenViewModel>()
                ProfileDetailsScreen(
                    profileDetailsScreenViewModel,
                    mainNavController)
            }
        }
    }
}

