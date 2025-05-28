package myapp.catscatalogexpanded.ui.leaderBoardScreen.ui

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.LeaderboardResponse
import myapp.catscatalogexpanded.ui.leaderBoardScreen.repository.LeaderboardRespository
import myapp.catscatalogexpanded.ui.quizScreen.model.QuizResultDB
import myapp.catscatalogexpanded.ui.quizScreen.repository.QuizResultDBRepository
import myapp.catscatalogexpanded.ui.quizScreen.repository.QuizResultDao
import myapp.catscatalogexpanded.users.UserRepository
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LeaderboardScreenViewModel @Inject constructor(
    private val leaderboardRepository: LeaderboardRespository,
    private val quizResultDBRepository: QuizResultDBRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _submission = MutableStateFlow<Result<LeaderboardResponse>?>(null)
    val submission = _submission.asStateFlow()

    private val _state = MutableStateFlow(LeaderboardScreenContract.UIState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun onEvent(event: LeaderboardScreenContract.UIEvent) {
        when (event) {
            LeaderboardScreenContract.UIEvent.LoadLeaderboard,
            LeaderboardScreenContract.UIEvent.Retry -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, results = emptyList(), error = null)

            try {
                val list = leaderboardRepository.fetchLeaderboard()
                _state.value = _state.value.copy(isLoading = false, results = list)

                val currentUser = userRepository.getUser()?.userName

                val formatter =
                    DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.getDefault())
                        .withZone(ZoneId.systemDefault())

                val myUserEntities = list
                    .mapIndexedNotNull { index, qr ->
                        if (qr.nickName == currentUser) {
                            QuizResultDB(
                                userName = qr.nickName,
                                score = qr.result,
                                timestamp = formatter.format(Instant.ofEpochMilli(qr.createdAt)),
                                rank = (index + 1).toLong()
                            )
                        } else null
                    }

                quizResultDBRepository.insertQuizResults(myUserEntities)

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }


    fun submitScore(nickname: String, result: Float) {
        viewModelScope.launch {
            try {
                val response = leaderboardRepository.submitScore(nickname, result)
                _submission.value = Result.success(response)
            } catch (e: Exception) {
                _submission.value = Result.failure(e)
            }
        }
    }

    fun addQuizToDatabase(nickname: String, result: Float, rank: Long, timeStamp: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val formatter =
                    DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.getDefault())
                        .withZone(ZoneId.systemDefault())

                quizResultDBRepository.insertQuizResult(
                    QuizResultDB(
                        userName = nickname,
                        score = result,
                        timestamp = formatter.format(Instant.ofEpochMilli(timeStamp)),
                        rank = rank
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(error = e.message)
                }
            }
        }
    }

    fun addQuizToDatabase(nickname: String, result: Float, timeStamp: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val formatter =
                    DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.getDefault())
                        .withZone(ZoneId.systemDefault())

                quizResultDBRepository.insertQuizResult(
                    QuizResultDB(
                        userName = nickname,
                        score = result,
                        timestamp = formatter.format(Instant.ofEpochMilli(timeStamp)),
                        rank = -1
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(error = e.message)
                }
            }
        }
    }
}