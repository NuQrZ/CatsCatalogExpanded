package myapp.catscatalogexpanded.ui.utilities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Breeds : BottomNavItem("breeds", Icons.Default.Pets, "Breeds")
    object Quiz : BottomNavItem("quiz", Icons.Default.Psychology, "Quiz")
    object Leaderboard : BottomNavItem("leaderboard", Icons.Default.EmojiEvents, "Leaderboard")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

