package myapp.catscatalogexpanded.ui.leaderBoardScreen.repository

import androidx.room.Query
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.LeaderboardRequest
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.LeaderboardResponse
import myapp.catscatalogexpanded.ui.leaderBoardScreen.data.QuizResult
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LeaderboardApi {
    @POST("leaderboard")
    suspend fun postScore(
        @Body request: LeaderboardRequest
    ): LeaderboardResponse

    @GET("leaderboard?category=1")
    suspend fun getLeaderboard(): List<QuizResult>
}