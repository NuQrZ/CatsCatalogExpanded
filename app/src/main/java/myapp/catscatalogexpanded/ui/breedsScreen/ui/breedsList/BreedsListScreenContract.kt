package myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList

import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed

interface BreedsListScreenContract {
    data class UIState(
        val loading: Boolean = true,
        val data: List<Breed> = emptyList(),
        val error: Throwable? = null,
        val showSearch: Boolean = false,
        val searchQuery: String = ""
    )

    sealed class UIEvent {
        data object RefreshData : UIEvent()
        data object ShowSearch : UIEvent()
        data object CloseSearch : UIEvent()
        data object ResetQuery : UIEvent()
        data class SearchData(val searchQuery: String) : UIEvent()
    }
}