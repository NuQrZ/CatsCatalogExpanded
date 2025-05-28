package myapp.catscatalogexpanded.ui.breedsScreen.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val name: String,
    @SerializedName("alt_names") val altNames: String? = null,
    val description: String,
    val temperament: String,
    @SerializedName("origin") val originCountry: String,
    @SerializedName("life_span") val lifeSpan: String,
    val weight: Weight,
    val image: BreedImage? = null,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val vocalisation: Int,
    @SerializedName("rare") val isRare: Int,
    @SerializedName("wikipedia_url") val wikipediaUrl: String? = null,
)

@Serializable
data class Weight(
    val metric: String
)

@Serializable
data class BreedImage(
    val url: String
)