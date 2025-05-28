package myapp.catscatalogexpanded.ui.registrationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.users.User
import myapp.catscatalogexpanded.users.UserRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationScreenContract.UIState())
    val state = _state.asStateFlow()

    private val _effect = Channel<RegistrationScreenContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: RegistrationScreenContract.UIEvent) {
        when (event) {
            is RegistrationScreenContract.UIEvent.NameChanged -> {
                _state.update { it.copy(name = event.value) }
            }

            is RegistrationScreenContract.UIEvent.UsernameChanged -> {
                _state.update { it.copy(userName = event.value) }
            }

            is RegistrationScreenContract.UIEvent.EmailChanged -> {
                _state.update { it.copy(email = event.value) }
            }

            is RegistrationScreenContract.UIEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.value) }
            }

            is RegistrationScreenContract.UIEvent.SubmitRegistration -> {
                registerUser()
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            val current = _state.value
            if (current.name.isBlank() || current.userName.isBlank() ||
                current.email.isBlank() || current.password.isBlank()
            ) {
                _effect.send(RegistrationScreenContract.Effect.ShowNotification("Please fill all fields!"))
                return@launch
            }

            val existingUser = userRepository.getUserByUserName(current.userName)
            if (existingUser != null) {
                _effect.send(RegistrationScreenContract.Effect.ShowNotification("Username already exists!"))
                return@launch
            }

            val newUser = User(
                name = current.name,
                userName = current.userName,
                email = current.email,
                password = current.password
            )

            userRepository.registerUser(newUser)
            _effect.send(RegistrationScreenContract.Effect.NavigateToHome)
        }
    }
}