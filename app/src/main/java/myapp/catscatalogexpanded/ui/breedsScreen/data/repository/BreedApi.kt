package myapp.catscatalogexpanded.ui.breedsScreen.data.repository

import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed
import retrofit2.http.GET

interface BreedApi {
    @GET("v1/breeds")
    suspend fun getBreeds(): List<Breed>
    suspend fun getBreedByID(breedID: String): Breed?
}