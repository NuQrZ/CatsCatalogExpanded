package myapp.catscatalogexpanded.ui.breedsScreen.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed
import myapp.catscatalogexpanded.ui.breedsScreen.data.remote.BreedRetrofitInstance
import javax.inject.Inject

class BreedApiImplementation @Inject constructor() : BreedApi {
    private val catApiService = BreedRetrofitInstance.retrofitInstance

    override suspend fun getBreeds(): List<Breed> {
        return withContext(Dispatchers.IO) {
            catApiService.getBreeds()
        }
    }

    override suspend fun getBreedByID(breedID: String): Breed? {
        return withContext(Dispatchers.IO) {
            catApiService.getBreeds().find { it.id == breedID }
        }
    }
}