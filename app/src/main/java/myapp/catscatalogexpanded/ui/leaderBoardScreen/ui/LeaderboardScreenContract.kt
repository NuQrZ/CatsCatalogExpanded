package myapp.catscatalogexpanded.ui.leaderBoardScreen.ui

import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.QuizResult

interface LeaderboardScreenContract {
    data class UIState(
        val isLoading: Boolean = false,
        val results: List<QuizResult> = emptyList(),
        val error: String? = null
    )

    sealed class UIEvent {
        object LoadLeaderboard : UIEvent()
        object Retry : UIEvent()
    }
}