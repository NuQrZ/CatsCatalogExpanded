package myapp.catscatalogexpanded.ui.leaderBoardScreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.AppTopBar
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.LoadingIndicator
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.NoDataContext
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.QuizResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    leaderboardScreenViewModel: LeaderboardScreenViewModel
) {
    val state = leaderboardScreenViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                text = "Leaderboard",
                leftIcon = Icons.Default.Refresh,
                leftIconClick = {
                    leaderboardScreenViewModel.onEvent(LeaderboardScreenContract.UIEvent.Retry)
                },
                rightIcon = null,
                rightIconClick = null
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                state.value.isLoading -> LoadingIndicator(modifier = Modifier.weight(1f))
                state.value.error != null -> NoDataContext(
                    text = "Error: ${state.value.error}",
                    modifier = Modifier.weight(1f)
                )

                state.value.results.isEmpty() -> NoDataContext(
                    text = "No Data",
                    modifier = Modifier.weight(1f)
                )

                else -> LeaderboardListColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    data = state.value.results
                )
            }
        }
    }
}

@Composable
private fun LeaderboardListColumn(
    modifier: Modifier,
    data: List<QuizResult>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = data,
            key = { index, _ -> index }
        ) { index, result ->
            LeaderboardCardItem(
                rank = index + 1,
                data = result
            )
        }
    }
}

@Composable
private fun LeaderboardCardItem(
    rank: Int,
    data: QuizResult
) {
    val cardColor = when (rank) {
        1 -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        2 -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
        3 -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
        else -> MaterialTheme.colorScheme.surface
    }

    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = cardColor),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "#$rank",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = data.nickName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${data.result}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            val formattedDate = remember(data.createdAt) {
                java.text.DateFormat
                    .getDateTimeInstance(java.text.DateFormat.MEDIUM, java.text.DateFormat.SHORT)
                    .format(java.util.Date(data.createdAt))
            }
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
