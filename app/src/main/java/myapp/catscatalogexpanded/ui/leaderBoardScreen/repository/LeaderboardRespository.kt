package myapp.catscatalogexpanded.ui.leaderBoardScreen.repository

import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.LeaderboardRequest
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.LeaderboardResponse
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.QuizResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaderboardRespository @Inject constructor(
    private val leaderboardApi: LeaderboardApi
) {
    suspend fun submitScore(nickname: String, result: Float): LeaderboardResponse {
        val request = LeaderboardRequest(nickName = nickname, result = result)
        return leaderboardApi.postScore(request)
    }

    suspend fun fetchLeaderboard(): List<QuizResult> {
        return leaderboardApi.getLeaderboard()
    }
}