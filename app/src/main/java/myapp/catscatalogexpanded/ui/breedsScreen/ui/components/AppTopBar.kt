package myapp.catscatalogexpanded.ui.breedsScreen.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@ExperimentalMaterial3Api
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    text: String,
    leftIcon: ImageVector? = null,
    leftIconClick: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    rightIconClick: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        navigationIcon = {
            if (leftIcon != null) {
                AppTopBarIcon(
                    icon = leftIcon,
                    onClick = { leftIconClick?.invoke() },
                )
            }
        },
        title = {
            Text(text = text)
        },
        actions = {
            if (rightIcon != null) {
                AppTopBarIcon(
                    icon = rightIcon,
                    onClick = { rightIconClick?.invoke() }
                )
            }
        },
    )
}