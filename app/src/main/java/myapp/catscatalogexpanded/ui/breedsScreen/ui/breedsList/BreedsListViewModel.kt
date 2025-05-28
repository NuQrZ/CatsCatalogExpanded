package myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList.BreedsListScreenContract.UIEvent
import myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsList.BreedsListScreenContract.UIState
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.BreedDB
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedConverter.toApi
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedConverter.toDb
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedDBRepository
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedApiImplementation

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    private val repository: BreedApiImplementation,
    private val repositoryDB: BreedDBRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {
        loadBreeds()
    }

    fun setEvent(event: UIEvent) {
        when (event) {
            UIEvent.RefreshData -> {
                _state.value = _state.value.copy(loading = true, data = emptyList())
                loadBreeds()
            }

            UIEvent.ShowSearch -> {
                _state.value = _state.value.copy(showSearch = true)
            }

            UIEvent.CloseSearch -> {
                _state.value = _state.value.copy(showSearch = false)
            }

            UIEvent.ResetQuery -> {
                _state.value = _state.value.copy(searchQuery = "")
            }

            is UIEvent.SearchData -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
            }
        }
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val count = repositoryDB.getBreedCount()

                if (count == 0) {
                    val apiBreeds: List<Breed> = repository.getBreeds()

                    val entities = apiBreeds.map { it.toDb() }

                    repositoryDB.insertAllBreeds(entities)

                    _state.value = _state.value.copy(
                        loading = false,
                        data = apiBreeds
                    )
                } else {
                    val cached: List<BreedDB> = repositoryDB.getBreedsDB()

                    val breedsFromDB: List<Breed> = cached.map { it.toApi() }

                    _state.value = _state.value.copy(
                        loading = false,
                        data = breedsFromDB
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e
                )
            }
        }
    }
}