package myapp.catscatalogexpanded.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.users.User
import myapp.catscatalogexpanded.users.UserRepository
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _hasCheckedUser = MutableStateFlow(false)
    val hasCheckedUser = _hasCheckedUser.asStateFlow()

    private val _count = MutableStateFlow<Int>(0)
    val count = _count.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val c = userRepository.getUserCount()
            val u = userRepository.getUser()
            _user.value = u
            _count.value = c
            _hasCheckedUser.value = true
        }
    }
}
