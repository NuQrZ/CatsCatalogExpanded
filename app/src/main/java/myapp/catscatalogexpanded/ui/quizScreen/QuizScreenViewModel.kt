package myapp.catscatalogexpanded.ui.quizScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedConverter.toApi
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedDBRepository
import myapp.catscatalogexpanded.ui.quizScreen.model.QuestionGenerator
import javax.inject.Inject

@HiltViewModel
class QuizScreenViewModel @Inject constructor(
    private val breedRepository: BreedDBRepository
) : ViewModel() {
    private val _state = MutableStateFlow(QuizScreenContract.UIState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    fun onEvent(event: QuizScreenContract.UIEvent) {
        when (event) {
            is QuizScreenContract.UIEvent.StartQuiz -> startQuiz()
            is QuizScreenContract.UIEvent.AnswerQuestion -> answer(event.selectedIndex)
            is QuizScreenContract.UIEvent.NextQuestion -> goToNext()
            is QuizScreenContract.UIEvent.FinishQuiz -> finishQuiz()
            is QuizScreenContract.UIEvent.CancelQuiz -> cancelQuiz()
            is QuizScreenContract.UIEvent.PauseTimer -> pauseTimer()
            is QuizScreenContract.UIEvent.ResumeTimer -> startTimer()
            is QuizScreenContract.UIEvent.Tick -> tick()
        }
    }

    private fun startQuiz() {
        timerJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            val allBreeds = breedRepository.getBreedsDB().map { it.toApi() }
            val questions = QuestionGenerator.generateQuestions(allBreeds)
            _state.value = _state.value.copy(questions = questions)
            startTimer()
        }
    }

    private fun answer(selectedIndex: Int) {
        val updatedAnswers =
            _state.value.answers + (_state.value.currentQuestionIndex to selectedIndex)
        _state.value = _state.value.copy(answers = updatedAnswers)
    }

    private fun goToNext() {
        if (_state.value.currentQuestionIndex < _state.value.questions.lastIndex) {
            _state.value =
                _state.value.copy(currentQuestionIndex = _state.value.currentQuestionIndex + 1)
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        timerJob?.cancel()
        _state.value = _state.value.copy(isQuizFinished = true, showResultDialog = true)
    }

    private fun cancelQuiz() {
        timerJob?.cancel()
        _state.value = _state.value.copy(isQuizFinished = true, showResultDialog = true)
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun tick() {
        if (_state.value.timeLeft > 0) {
            _state.value = _state.value.copy(timeLeft = _state.value.timeLeft - 1)
        } else {
            finishQuiz()
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_state.value.timeLeft >= 0) {
                delay(1000)
                onEvent(QuizScreenContract.UIEvent.Tick)
            }
        }
    }

    fun calculateScore(): Float {
        val correctAnswers = _state.value.answers.filter { (index, answer) ->
            _state.value.questions[index].correctIndex == answer
        }.count()
        val timeLeft = _state.value.timeLeft
        val finalPoints = correctAnswers * 2.5f * (1 + (timeLeft + 120f) / 300)
        return finalPoints.coerceAtMost(100.0f)
    }
}