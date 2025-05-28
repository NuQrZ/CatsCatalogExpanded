package myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsDetails

import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed

interface BreedsDetailsScreenContract {
    data class UIState(
        val breedID: String,
        val loading: Boolean = true,
        val data: Breed? = null
    )
}