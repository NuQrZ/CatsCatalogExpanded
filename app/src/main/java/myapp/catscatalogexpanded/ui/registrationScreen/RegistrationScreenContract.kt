package myapp.catscatalogexpanded.ui.registrationScreen

interface RegistrationScreenContract {
    data class UIState(
        val name: String = "",
        val userName: String = "",
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    )

    sealed class UIEvent {
        data class NameChanged(val value: String) : UIEvent()
        data class UsernameChanged(val value: String) : UIEvent()
        data class EmailChanged(val value: String) : UIEvent()
        data class PasswordChanged(val value: String) : UIEvent()
        object SubmitRegistration : UIEvent()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
        data class ShowNotification(val message: String) : Effect()
    }
}