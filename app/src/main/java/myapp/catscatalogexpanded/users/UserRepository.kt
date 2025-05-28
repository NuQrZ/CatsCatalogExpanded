package myapp.catscatalogexpanded.users

import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUserCount(): Int {
        return userDao.userCount()
    }

    suspend fun registerUser(user: User): Boolean {
        val exists = userDao.getUserByEmail(user.email)
        return if (exists == null) {
            userDao.registerUser(user)
            true
        } else false
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

    suspend fun getUser(): User? {
        return userDao.getUser()
    }

    suspend fun getUserByUserName(userName: String): User? {
        return userDao.getUserByUserName(userName)
    }
}