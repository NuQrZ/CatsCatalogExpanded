package myapp.catscatalogexpanded.ui.loginScreen

interface LoginScreenContract {
    data class UIState(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    )

    sealed class UIEvent {
        data class EmailChanged(val value: String) : UIEvent()
        data class PasswordChanged(val value: String) : UIEvent()
        object SubmitLogin : UIEvent()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
        data class ShowNotification(val message: String) : Effect()
    }
}