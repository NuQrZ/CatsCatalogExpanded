package myapp.catscatalogexpanded.ui.breedsScreen.data.repository

import myapp.catscatalogexpanded.ui.breedsScreen.data.model.BreedDB
import javax.inject.Inject

class BreedDBRepository @Inject constructor(
    private val breedDao: BreedDao
) {
    suspend fun insertAllBreeds(breedDBList: List<BreedDB>) {
        breedDao.insertAllBreeds(breedDBList)
    }

    suspend fun getBreedsDB(): List<BreedDB> {
        return breedDao.getBreedsDB()
    }

    suspend fun getBreedByID(breedID: String): BreedDB {
        return breedDao.getBreedByID(breedID)
    }

    suspend fun getBreedCount(): Int {
        return breedDao.getBreedCount()
    }
}