package myapp.catscatalogexpanded.ui.profileDetailsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import myapp.catscatalogexpanded.ui.breedsScreen.ui.components.AppTopBar
import myapp.catscatalogexpanded.users.User
import myapp.catscatalogexpanded.ui.quizScreen.model.QuizResultDB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsScreen(
    profileDetailsScreenViewModel: ProfileDetailsScreenViewModel,
    mainNavController: NavController
) {
    val state = profileDetailsScreenViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                text = "Profile",
                leftIcon = null,
                leftIconClick = null,
                rightIcon = Icons.AutoMirrored.Filled.Logout,
                rightIconClick = {
                    mainNavController.navigate("login")
                }
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item { SectionHeader("Account Info") }
            item { AccountInfoCard(state.value.user) }

            item { SectionHeader("Your Stats") }
            item { BestScoreCard(state.value.bestResult?.score) }
            item { BestGlobalRankCard(state.value.bestResult?.rank) }

            item { SectionHeader("Quiz History") }
            item { QuizHistoryCard(state.value.quizzesDetails) }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(start = 24.dp, top = 4.dp, bottom = 4.dp)
    )
}

@Composable
private fun AccountInfoCard(user: User?) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Name", style = MaterialTheme.typography.labelMedium)
            Text(user?.name.orEmpty(), style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))

            Text("Username", style = MaterialTheme.typography.labelMedium)
            Text(user?.userName.orEmpty(), style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))

            Text("Email", style = MaterialTheme.typography.labelMedium)
            Text(user?.email.orEmpty(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun BestScoreCard(bestScore: Float?) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Text("Best Score", style = MaterialTheme.typography.labelMedium)
            Text(
                text = bestScore?.toString() ?: "—",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun BestGlobalRankCard(bestRank: Long?) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Global Rank", style = MaterialTheme.typography.labelMedium)
            Text(
                text = bestRank?.let { "#$it" } ?: "Unranked",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun QuizHistoryCard(quizzes: List<QuizResultDB>) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            if (quizzes.isEmpty()) {
                Text(
                    text = "No quiz attempts yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                val sortedQuizzes = quizzes.sortedWith(
                    compareBy<QuizResultDB> { it.rank.toInt() == -1 }
                        .thenBy { it.rank }
                        .thenByDescending { it.score }
                )
                sortedQuizzes.forEachIndexed { index, entry ->
                    QuizHistoryRow(entry)
                    if (index < quizzes.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizHistoryRow(entry: QuizResultDB) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = entry.score.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = entry.timestamp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = if (entry.rank.toInt() != -1) "#${entry.rank}" else "#-",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
