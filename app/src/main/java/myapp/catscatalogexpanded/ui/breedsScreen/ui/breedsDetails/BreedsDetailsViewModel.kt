package myapp.catscatalogexpanded.ui.breedsScreen.ui.breedsDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import myapp.catscatalogexpanded.ui.breedsScreen.breedsNavigation.breedIdOrThrow
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedConverter.toApi
import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedDBRepository
import javax.inject.Inject

@HiltViewModel
class BreedsDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repositoryDB: BreedDBRepository
) : ViewModel() {
    private val breedID = savedStateHandle.breedIdOrThrow

    private val _state = MutableStateFlow(BreedsDetailsScreenContract.UIState(breedID = breedID))
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedsDetailsScreenContract.UIState.() -> BreedsDetailsScreenContract.UIState) =
        _state.getAndUpdate(reducer)

    init {
        loadBreed(breedID)
    }

    private fun loadBreed(breedID: String) = viewModelScope.launch {
        try {
            setState { copy(loading = true) }
            val breedData = repositoryDB.getBreedByID(breedID).toApi()
            setState {
                copy(
                    data = breedData,
                    loading = false
                )
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) }
        }
    }
}