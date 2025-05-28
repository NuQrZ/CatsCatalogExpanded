package myapp.catscatalogexpanded.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true) val userID: Int = 0,
    val name: String,
    val userName: String,
    val email: String,
    val password: String
)