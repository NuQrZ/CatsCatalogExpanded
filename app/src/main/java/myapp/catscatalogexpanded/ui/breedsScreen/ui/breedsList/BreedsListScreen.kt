package myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.AppTopBarIcon
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.AppTopBar
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.LoadingIndicator
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.NoDataContext

@Composable
fun BreedsListScreen(
    viewModel: BreedsListViewModel,
    onBreedListItemClick: (String) -> Unit,
    onQuizButtonClick: () -> Unit
) {
    val uiState = viewModel.state.collectAsState()

    BreedsList(
        state = uiState.value,
        eventPublisher = { viewModel.setEvent(it) },
        onBreedListItemClick = onBreedListItemClick,
        onQuizButtonClick = onQuizButtonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreedsList(
    state: BreedsListScreenContract.UIState,
    eventPublisher: (BreedsListScreenContract.UIEvent) -> Unit,
    onBreedListItemClick: (String) -> Unit,
    onQuizButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                text = "Cat Breeds",
                leftIcon = Icons.Default.Refresh,
                leftIconClick = {
                    eventPublisher(BreedsListScreenContract.UIEvent.RefreshData)
                },
                rightIcon = Icons.Default.Search,
                rightIconClick = {
                    eventPublisher(BreedsListScreenContract.UIEvent.ShowSearch)
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .offset(x = (-16).dp, y = (-16).dp),
                text = { Text("Quiz") },
                icon = { Icon(Icons.Default.Quiz, contentDescription = "Start quiz") },
                onClick = onQuizButtonClick,
                containerColor = MaterialTheme.colorScheme.primary
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.showSearch) {
                TextField(
                    value = state.searchQuery,
                    onValueChange = {
                        eventPublisher(BreedsListScreenContract.UIEvent.SearchData(it))
                    },
                    placeholder = { Text("Search breeds...") },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                if (state.searchQuery.isNotEmpty()) {
                                    eventPublisher(BreedsListScreenContract.UIEvent.ResetQuery)
                                } else {
                                    eventPublisher(BreedsListScreenContract.UIEvent.CloseSearch)
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close search"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            val list = if (state.searchQuery.isBlank()) {
                state.data
            } else {
                state.data.filter {
                    it.name.contains(state.searchQuery, ignoreCase = true)
                }
            }

            when {
                state.loading -> LoadingIndicator(modifier = Modifier.weight(1f))
                state.error != null -> NoDataContext(
                    text = "Error: ${state.error.message}",
                    modifier = Modifier.weight(1f)
                )

                list.isEmpty() -> NoDataContext(
                    text = "No data!",
                    modifier = Modifier.weight(1f)
                )

                else -> BreedListColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    data = list,
                    onBreedsListClick = onBreedListItemClick
                )
            }
        }
    }
}

@Composable
private fun BreedListColumn(
    modifier: Modifier,
    data: List<Breed>,
    onBreedsListClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = data.size, key = { data[it].id }) { index ->
            val breed = data[index]
            BreedsListItem(
                data = breed,
                onClick = { onBreedsListClick(breed.id) }
            )
        }
    }
}

@Composable
private fun BreedsListItem(
    data: Breed,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(text = data.name) },
        supportingContent = { Text(text = data.originCountry) },
        leadingContent = {
            AsyncImage(
                model = data.image?.url,
                contentDescription = "Cat image",
                modifier = Modifier.size(40.dp, 40.dp)
            )
        },
        trailingContent = {
            AppTopBarIcon(
                icon = Icons.Default.ChevronRight,
                onClick = onClick
            )
        }
    )
}
