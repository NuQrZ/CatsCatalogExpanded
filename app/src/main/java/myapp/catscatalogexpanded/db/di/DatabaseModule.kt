package myapp.catscatalogexpanded.db.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import myapp.catscatalogexpanded.db.AppDatabase
import myapp.catscatalogexpanded.db.AppDatabaseBuilder
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedDao
import myapp.catscatalogexpanded.ui.quizScreen.repository.QuizResultDao
import myapp.catscatalogexpanded.users.UserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(builder: AppDatabaseBuilder): AppDatabase {
        return builder.build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideBreedDao(database: AppDatabase): BreedDao {
        return database.breedDao()
    }

    @Provides
    fun provideQuizResultsDao(database: AppDatabase): QuizResultDao {
        return database.quizResultDao()
    }
}
