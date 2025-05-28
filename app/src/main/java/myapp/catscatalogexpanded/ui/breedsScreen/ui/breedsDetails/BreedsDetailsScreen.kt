package myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.BreedDetailsContent
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.AppTopBar
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.LoadingIndicator
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.NoDataContext

@Composable
fun BreedsDetailsScreen(
    viewModel: BreedsDetailsViewModel,
    onClose: () -> Unit
) {
    val uiState = viewModel.state.collectAsState()

    BreedsDetailsScreen(
        state = uiState.value,
        onClose = onClose
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreedsDetailsScreen(
    state: BreedsDetailsScreenContract.UIState,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                text = "Breed Details",
                leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
                leftIconClick = onClose,
                rightIcon = null,
                rightIconClick = null
            )
        }
    ) { padding ->
        if (state.loading) {
            LoadingIndicator()
        } else if (state.data != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {
                val breed = state.data
                BreedDetailsContent(breed = breed)
            }
        } else {
            NoDataContext(
                text = "Error = Breed with id ${state.breedID} not found.",
            )
        }
    }
}