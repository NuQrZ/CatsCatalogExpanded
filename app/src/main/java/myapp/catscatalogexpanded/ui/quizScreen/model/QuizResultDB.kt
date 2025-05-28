package myapp.catscatalogexpanded.ui.quizScreen.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "QuizResults",
    indices = [
        Index(value = ["userName", "score", "timestamp", "rank"], unique = true)
    ]
)
data class QuizResultDB (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userName: String,
    val score: Float,
    val timestamp: String,
    val rank: Long
)