package myapp.catscatalogexpanded.ui.quizScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import myapp.catscatalogexpanded.navigation.AppViewModel
import myapp.catscatalogexpanded.ui.leaderBoardScreen.ui.LeaderboardScreenViewModel

@Composable
fun ResultScreen(
    mainNavController: NavController,
    leaderboardScreenViewModel: LeaderboardScreenViewModel,
    appViewModel: AppViewModel,
    score: Float,
    onFinish: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your result is: $score", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        val user = appViewModel.user.collectAsState()
        val nickname = user.value?.userName
        val submission = leaderboardScreenViewModel.submission.collectAsState()

        Button(onClick = {
            onFinish()
            leaderboardScreenViewModel.addQuizToDatabase(nickname.toString(), score, System.currentTimeMillis())
            mainNavController.navigate("home")
        }
        ) {
            Text("Back to Home")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            nickname?.let { leaderboardScreenViewModel.submitScore(nickname = it, result = score) }
            mainNavController.navigate("home")
        }
        ) {
            Text("Publish score")
        }

        submission.value?.let { result ->
            result.onSuccess { response ->
                Text("Published! Your global rank: ${response.ranking}")
                leaderboardScreenViewModel.addQuizToDatabase(nickname.toString(), score, response.ranking.toLong(), response.result.createdAt)
            }.onFailure { error ->
                Text("Failed to publish: ${error.localizedMessage}")
            }
        }
    }
}