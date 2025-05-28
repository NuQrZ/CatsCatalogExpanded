package myapp.catscatalogexpanded.ui.breedsScreen.data.repository

import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.BreedDB
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.BreedImage
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.ImageDB
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Weight
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.WeightDB

object BreedConverter {
    fun BreedDB.toApi(): Breed = Breed(
        id = id,
        name = name,
        altNames = altNames,
        description = description,
        temperament = temperament,
        originCountry = originCountry,
        lifeSpan = lifeSpan,
        weight = Weight(metric = weight.metric),
        image = image?.let { BreedImage(url = it.url) },
        adaptability = adaptability,
        affectionLevel = affectionLevel,
        childFriendly = childFriendly,
        dogFriendly = dogFriendly,
        energyLevel = energyLevel,
        grooming = grooming,
        healthIssues = healthIssues,
        intelligence = intelligence,
        sheddingLevel = sheddingLevel,
        socialNeeds = socialNeeds,
        strangerFriendly = strangerFriendly,
        vocalisation = vocalisation,
        isRare = isRare,
        wikipediaUrl = wikipediaUrl
    )

    fun Breed.toDb(): BreedDB = BreedDB(
        id = id,
        name = name,
        altNames = altNames,
        description = description,
        temperament = temperament,
        originCountry = originCountry,
        lifeSpan = lifeSpan,
        weight = WeightDB(metric = weight.metric),
        image = image?.let { ImageDB(url = it.url) },
        adaptability = adaptability,
        affectionLevel = affectionLevel,
        childFriendly = childFriendly,
        dogFriendly = dogFriendly,
        energyLevel = energyLevel,
        grooming = grooming,
        healthIssues = healthIssues,
        intelligence = intelligence,
        sheddingLevel = sheddingLevel,
        socialNeeds = socialNeeds,
        strangerFriendly = strangerFriendly,
        vocalisation = vocalisation,
        isRare = isRare,
        wikipediaUrl = wikipediaUrl
    )
}