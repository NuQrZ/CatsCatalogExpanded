package myapp.catscatalogexpanded.ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.users.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenContract.UIState())
    val state = _state.asStateFlow()

    private val _effect = Channel<LoginScreenContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: LoginScreenContract.UIEvent) {
        when (event) {
            is LoginScreenContract.UIEvent.EmailChanged -> {
                _state.update { it.copy(email = event.value) }
            }

            is LoginScreenContract.UIEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.value) }
            }

            LoginScreenContract.UIEvent.SubmitLogin -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    val user = userRepository.login(
                        _state.value.email,
                        _state.value.password
                    )

                    if (user != null) {
                        _effect.send(LoginScreenContract.Effect.NavigateToHome)
                    }
                    else {
                        val message = "Invalid Credentials!"
                        _effect.send(LoginScreenContract.Effect.ShowNotification(message))
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}