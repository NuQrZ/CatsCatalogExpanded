package myapp.catscatalogexpanded.ui.breedsScreen.breedsNavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsDetails.BreedsDetailsScreen
import myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsDetails.BreedsDetailsViewModel
import myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList.BreedsListScreen
import myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList.BreedsListViewModel
import myapp.catscatalogexpanded.ui.utilities.BottomNavItem

private fun NavController.navigateToDetails(breedID: String) {
    this.navigate(route = "details/$breedID")
}

@Composable
fun BreedNavigation(
    mainNavController: NavController
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breedsList"
    ) {
        breedsList(
            route = "breedsList",
            navController = navController,
            mainNavController = mainNavController
        )

        breedsDetails(
            route = "details/{$BREED_ID_ARG}",
            navController = navController
        )
    }
}

private fun NavGraphBuilder.breedsList(
    route: String,
    navController: NavController,
    mainNavController: NavController
) = composable(route = route) {
    val viewModel = hiltViewModel<BreedsListViewModel>()

    BreedsListScreen(
        viewModel = viewModel,
        onBreedListItemClick = { breedID ->
            navController.navigateToDetails(breedID = breedID)
        },
        onQuizButtonClick = {
            mainNavController.navigate(BottomNavItem.Quiz.route)
        }
    )
}

private fun NavGraphBuilder.breedsDetails(
    route: String,
    navController: NavController
) = composable(route = route) {
    val viewModel = hiltViewModel<BreedsDetailsViewModel>()
    BreedsDetailsScreen(
        viewModel = viewModel,
        onClose = {
            navController.navigateUp()
        }
    )
}