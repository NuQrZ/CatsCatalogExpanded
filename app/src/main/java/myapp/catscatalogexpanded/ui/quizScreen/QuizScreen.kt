package myapp.catscatalogexpanded.ui.quizScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import myapp.catscatalogexpanded.navigation.AppViewModel
import myapp.catscatalogexpanded.theme.Purple40
import myapp.catscatalogexpanded.theme.Purple80
import myapp.catscatalogexpanded.ui.leaderBoardScreen.ui.LeaderboardScreenViewModel

@Composable
fun QuizScreen(
    mainNavController: NavController,
    quizViewModel: QuizScreenViewModel,
    onFinish: () -> Unit
) {
    val leaderboardScreenViewModel = hiltViewModel<LeaderboardScreenViewModel>()
    val appViewModel = hiltViewModel<AppViewModel>()
    val state by quizViewModel.state.collectAsState()
    val progress by animateFloatAsState(
        targetValue = (state.currentQuestionIndex + 1) / state.questions.size.toFloat(),
        label = "Progress"
    )

    var showConfirmExit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        quizViewModel.onEvent(QuizScreenContract.UIEvent.StartQuiz)
    }

    if (state.isQuizFinished) {
        ResultScreen(
            mainNavController = mainNavController,
            leaderboardScreenViewModel = leaderboardScreenViewModel,
            appViewModel = appViewModel,
            score = quizViewModel.calculateScore(),
            onFinish = onFinish,
        )
        return
    }

    if (showConfirmExit) {
        AlertDialog(
            onDismissRequest = { showConfirmExit = false },
            title = { Text("Exit Quiz?") },
            text = { Text("Are you sure you want to abandon the quiz? Your progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmExit = false
                    quizViewModel.onEvent(QuizScreenContract.UIEvent.CancelQuiz)
                }) {
                    Text("Yes, Exit")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmExit = false
                    quizViewModel.onEvent(QuizScreenContract.UIEvent.ResumeTimer)
                }) {
                    Text("Keep Playing")
                }
            }
        )
    }

    val current = state.questions.getOrNull(state.currentQuestionIndex) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Purple40, Purple80))
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(42.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    quizViewModel.onEvent(QuizScreenContract.UIEvent.PauseTimer)
                    showConfirmExit = true
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(42.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.12f)
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Question",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White
                            )
                        )
                        Text(
                            text = "${state.currentQuestionIndex + 1} / ${state.questions.size}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${state.timeLeft}s",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            AsyncImage(
                model = current.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            current.options.forEachIndexed { idx, option ->
                Button(
                    onClick = {
                        quizViewModel.onEvent(QuizScreenContract.UIEvent.AnswerQuestion(idx))
                        quizViewModel.onEvent(QuizScreenContract.UIEvent.NextQuestion)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = option,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}
