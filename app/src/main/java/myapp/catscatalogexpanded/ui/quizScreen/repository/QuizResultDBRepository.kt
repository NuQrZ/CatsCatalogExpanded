package myapp.catscatalogexpanded.ui.quizScreen.repository

import myapp.catscatalogexpanded.ui.quizScreen.model.QuizResultDB
import javax.inject.Inject

class QuizResultDBRepository @Inject constructor(
    private val quizResultDao: QuizResultDao
) {
    suspend fun insertQuizResults(quizResults: List<QuizResultDB>) {
        quizResultDao.insertQuizResults(quizResults)
    }

    suspend fun insertQuizResult(quizResultDB: QuizResultDB) {
        quizResultDao.insertQuizResult(quizResultDB)
    }

    suspend fun getAllResults(): List<QuizResultDB> {
        return quizResultDao.getAllResults()
    }

    suspend fun getRankByUserName(userName: String): Long {
        return quizResultDao.getRankByUserName(userName)
    }
}