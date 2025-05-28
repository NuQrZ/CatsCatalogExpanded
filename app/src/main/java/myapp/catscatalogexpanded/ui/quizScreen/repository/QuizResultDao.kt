package myapp.catscatalogexpanded.ui.quizScreen.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import myapp.catscatalogexpanded.ui.quizScreen.model.QuizResultDB

@Dao
interface QuizResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResults(results: List<QuizResultDB>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertQuizResult(quizResultDB: QuizResultDB)

    @Query("SELECT * FROM QuizResults ORDER BY timestamp DESC")
    suspend fun getAllResults(): List<QuizResultDB>

    @Query("SELECT rank FROM QuizResults WHERE userName = :userName")
    suspend fun getRankByUserName(userName: String): Long
}