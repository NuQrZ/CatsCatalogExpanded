package myapp.catscatalogexpanded.ui.leaderBoardScreen.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class LeaderboardRequest(
    @SerializedName("nickname")
    val nickName: String,
    val result: Float,
    val category: Int = 1
)

