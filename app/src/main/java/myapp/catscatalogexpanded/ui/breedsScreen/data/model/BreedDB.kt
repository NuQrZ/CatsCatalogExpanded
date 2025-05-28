package myapp.catscatalogexpanded.ui.breedsScreen.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Breeds")
data class BreedDB(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "alt_names")
    val altNames: String? = null,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "temperament")
    val temperament: String,

    @ColumnInfo(name = "origin_country")
    val originCountry: String,

    @ColumnInfo(name = "life_span")
    val lifeSpan: String,

    // flatten nested objects with @Embedded
    @Embedded(prefix = "weight_")
    val weight: WeightDB,

    @Embedded(prefix = "image_")
    val image: ImageDB? = null,

    @ColumnInfo(name = "adaptability")
    val adaptability: Int,

    @ColumnInfo(name = "affection_level")
    val affectionLevel: Int,

    @ColumnInfo(name = "child_friendly")
    val childFriendly: Int,

    @ColumnInfo(name = "dog_friendly")
    val dogFriendly: Int,

    @ColumnInfo(name = "energy_level")
    val energyLevel: Int,

    @ColumnInfo(name = "grooming")
    val grooming: Int,

    @ColumnInfo(name = "health_issues")
    val healthIssues: Int,

    @ColumnInfo(name = "intelligence")
    val intelligence: Int,

    @ColumnInfo(name = "shedding_level")
    val sheddingLevel: Int,

    @ColumnInfo(name = "social_needs")
    val socialNeeds: Int,

    @ColumnInfo(name = "stranger_friendly")
    val strangerFriendly: Int,

    @ColumnInfo(name = "vocalisation")
    val vocalisation: Int,

    @ColumnInfo(name = "is_rare")
    val isRare: Int,

    @ColumnInfo(name = "wikipedia_url")
    val wikipediaUrl: String? = null,
)

// You’ll need little wrapper types for the embedded classes:
data class WeightDB(
    @ColumnInfo(name = "metric") val metric: String
)

data class ImageDB(
    @ColumnInfo(name = "url") val url: String
)
