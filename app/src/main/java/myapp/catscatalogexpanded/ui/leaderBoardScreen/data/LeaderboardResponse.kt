package myapp.catscatalogexpanded.ui.leaderBoardScreen.data

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(
    @SerializedName("result")
    val result: QuizResult,
    @SerializedName("ranking")
    val ranking: Int
)