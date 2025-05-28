package myapp.catscatalogexpanded.ui.quizScreen

import myapp.catscatalogexpanded.ui.quizScreen.model.Question

interface QuizScreenContract {
    data class UIState(
        val questions: List<Question> = emptyList(),
        val currentQuestionIndex: Int = 0,
        val answers: Map<Int, Int> = emptyMap(),
        val timeLeft: Int = 100,
        val isQuizFinished: Boolean = false,
        val showResultDialog: Boolean = false,
    )

    sealed class UIEvent {
        object StartQuiz : UIEvent()
        data class AnswerQuestion(val selectedIndex: Int) : UIEvent()
        object NextQuestion : UIEvent()
        object FinishQuiz : UIEvent()
        object CancelQuiz : UIEvent()
        object PauseTimer : UIEvent()
        object ResumeTimer : UIEvent()
        object Tick : UIEvent()
    }
}