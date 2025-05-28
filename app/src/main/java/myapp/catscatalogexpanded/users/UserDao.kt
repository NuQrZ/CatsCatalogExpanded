package myapp.catscatalogexpanded.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: User)

    @Query("SELECT COUNT(*) FROM Users")
    suspend fun userCount(): Int

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM Users")
    suspend fun getUser(): User?

    @Query("SELECT * FROM Users WHERE userName = :username")
    suspend fun getUserByUserName(username: String): User?
}