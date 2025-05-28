package myapp.catscatalogexpanded.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import myapp.catscatalogexpanded.navigation.AppNavigation
import myapp.catscatalogexpanded.theme.CatsCatalogExpandedTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsCatalogExpandedTheme {
                AppNavigation()
            }
        }
    }
}