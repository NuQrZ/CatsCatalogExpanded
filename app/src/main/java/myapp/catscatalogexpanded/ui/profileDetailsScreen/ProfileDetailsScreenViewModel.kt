package myapp.catscatalogexpanded.ui.profileDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.ui.quizScreen.repository.QuizResultDBRepository
import myapp.catscatalogexpanded.users.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileDetailsScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val quizResultDBRepository: QuizResultDBRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileDetailsScreenContract.UIState())
    val state = _state.asStateFlow()

    init {
        setEvent(ProfileDetailsScreenContract.UIEvent.LoadProfile)
    }

    fun setEvent(event: ProfileDetailsScreenContract.UIEvent) {
        when (event) {
            is ProfileDetailsScreenContract.UIEvent.LoadProfile -> {
                loadUser()
                loadQuizzes()
                loadBestResult()
            }
        }
    }

    private fun loadUser() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val user = userRepository.getUser()
                _state.value = _state.value.copy(isLoading = true, user = user)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun loadQuizzes() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val quizzes = quizResultDBRepository.getAllResults()
                _state.value = _state.value.copy(quizzesDetails = quizzes, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun loadBestResult() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val quizzes = quizResultDBRepository.getAllResults()
                val bestScore = quizzes.maxByOrNull { it.score }
                _state.value = _state.value.copy(isLoading = false, bestResult = bestScore)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}