package myapp.catscatalogexpanded.ui.breedsScreen.breedsNavigation

import androidx.lifecycle.SavedStateHandle

const val BREED_ID_ARG = "breedId"

val SavedStateHandle.breedIdOrThrow: String
    get() = this.get<String>(BREED_ID_ARG) ?: error("$BREED_ID_ARG not found")