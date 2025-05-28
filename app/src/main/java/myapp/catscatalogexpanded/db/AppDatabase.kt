package myapp.catscatalogexpanded.db

import androidx.room.Database
import androidx.room.RoomDatabase
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.BreedDB
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedDao
import myapp.catscatalogexpanded.ui.quizScreen.model.QuizResultDB
import myapp.catscatalogexpanded.ui.quizScreen.repository.QuizResultDao
import myapp.catscatalogexpanded.users.User
import myapp.catscatalogexpanded.users.UserDao

@Database(
    entities = [
        User::class,
        BreedDB::class,
        QuizResultDB::class
    ],
    version = 13,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun breedDao(): BreedDao
    abstract fun quizResultDao(): QuizResultDao
}