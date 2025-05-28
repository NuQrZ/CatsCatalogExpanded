package myapp.catscatalogexpanded.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import myapp.catscatalogexpanded.ui.home.HomeScreen
import myapp.catscatalogexpanded.ui.loginScreen.LoginScreen
import myapp.catscatalogexpanded.ui.loginScreen.LoginScreenViewModel
import myapp.catscatalogexpanded.ui.quizScreen.QuizScreen
import myapp.catscatalogexpanded.ui.quizScreen.QuizScreenViewModel
import myapp.catscatalogexpanded.ui.registrationScreen.RegistrationScreen
import myapp.catscatalogexpanded.ui.registrationScreen.RegistrationScreenViewModel
import myapp.catscatalogexpanded.ui.utilities.BottomNavItem

@Composable
fun AppNavigation(
    appViewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val count by appViewModel.count.collectAsState()
    val hasChecked by appViewModel.hasCheckedUser.collectAsState()

    if (!hasChecked) {
        if (count > 0) {
            SplashScreen()
            return
        } else {
            return
        }
    }

    val start = if (count > 0) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = start
    ) {
        home("home", mainNavController = navController)

        login(route = "login", navController = navController)

        register(route = "register", navController = navController)

        quiz(BottomNavItem.Quiz.route, mainNavController = navController)
    }
}

@Composable
fun SplashScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Cats Catalog",
            style = MaterialTheme.typography.displayMedium
        )
    }
}

private fun NavGraphBuilder.login(
    route: String,
    navController: NavController
) = composable(route = route) {
    val loginViewModel = hiltViewModel<LoginScreenViewModel>()

    LoginScreen(
        viewModel = loginViewModel,
        onNavigateToHome = {
            navController.navigate("home")
        },
        onRegisterTextClick = {
            navController.navigate("register")
        }
    )
}

private fun NavGraphBuilder.register(
    route: String,
    navController: NavController
) = composable(route = route) {
    val registrationViewModel = hiltViewModel<RegistrationScreenViewModel>()

    RegistrationScreen(
        viewModel = registrationViewModel,
        onNavigateToHome = {
            navController.navigate("home")
        },
        onLoginTextClick = {
            navController.navigate("login")
        }
    )
}

private fun NavGraphBuilder.quiz(
    route: String,
    mainNavController: NavController
) = composable(route = route) {
    val viewModel = hiltViewModel<QuizScreenViewModel>()
    var showQuiz by remember { mutableStateOf(false) }

    if (showQuiz) {
        QuizScreen(
            mainNavController = mainNavController,
            quizViewModel = viewModel,
            onFinish = {
                mainNavController.navigate("home") {
                    popUpTo(BottomNavItem.Breeds.route)
                    launchSingleTop = true
                }
            }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { showQuiz = true }) {
                    Text("Start Quiz")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    mainNavController.navigate("home") {
                        popUpTo(BottomNavItem.Breeds.route)
                        launchSingleTop = true
                    }
                }) {
                    Text("Back to Home")
                }
            }
        }

    }
}

private fun NavGraphBuilder.home(
    route: String,
    mainNavController: NavController
) = composable(route = route) {
    HomeScreen(mainNavController = mainNavController)
}