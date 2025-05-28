package myapp.catscatalogexpanded.ui.breedsScreen.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.BreedDB

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAllBreeds(breedDBList: List<BreedDB>)

    @Query("SELECT * FROM Breeds")
    suspend fun getBreedsDB(): List<BreedDB>

    @Query("SELECT * FROM Breeds WHERE id = :breedID")
    suspend fun getBreedByID(breedID: String): BreedDB

    @Query("SELECT COUNT(*) FROM Breeds")
    suspend fun getBreedCount(): Int
}