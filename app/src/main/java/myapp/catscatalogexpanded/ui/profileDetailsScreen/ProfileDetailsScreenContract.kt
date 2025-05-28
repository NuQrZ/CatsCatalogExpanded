package myapp.catscatalogexpanded.ui.profileDetailsScreen

import myapp.catscatalogexpanded.ui.quizScreen.model.QuizResultDB
import myapp.catscatalogexpanded.users.User

interface ProfileDetailsScreenContract {
    data class UIState(
        val isLoading: Boolean = true,
        val user: User? = null,
        val quizzesDetails: List<QuizResultDB> = emptyList(),
        val bestResult: QuizResultDB? = null,
        val error: String? = null
    )

    sealed class UIEvent {
        object LoadProfile : UIEvent()
    }
}