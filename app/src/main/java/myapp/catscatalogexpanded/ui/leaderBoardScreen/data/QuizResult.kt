package myapp.catscatalogexpanded.ui.leaderBoardScreen.data

import com.google.gson.annotations.SerializedName

data class QuizResult (
    val category: Int,
    @SerializedName("nickname")
    val nickName: String,
    val result: Float,
    val createdAt: Long
)