package myapp.catscatalogexpanded.ui.breedsScreen.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed

@Composable
fun BreedDetailsContent(breed: Breed) {
    var scrollBar = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .verticalScroll(scrollBar),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        breed.image?.url?.let { url ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = "Breed Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }

            Text(
                text = breed.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Origin: ${breed.originCountry}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = breed.description,
                style = MaterialTheme.typography.bodyLarge
            )

            InfoChip(label = "Life Span", value = "${breed.lifeSpan} years")
            InfoChip(label = "Weight", value = "${breed.weight.metric} kg")
            InfoChip(label = "Temperament", value = breed.temperament)

            Text(
                text = "Traits",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            TraitStat(label = "Affection", value = breed.affectionLevel)
            TraitStat(label = "Energy", value = breed.energyLevel)
            TraitStat(label = "Intelligence", value = breed.intelligence)
            TraitStat(label = "Dog Friendly", value = breed.dogFriendly)
            TraitStat(label = "Child Friendly", value = breed.childFriendly)
            TraitStat(label = "Health Issues", value = breed.healthIssues)
            TraitStat(label = "Grooming", value = breed.grooming)
            TraitStat(label = "Shedding", value = breed.sheddingLevel)

            breed.wikipediaUrl?.let { url ->
                Text(
                    text = "Wikipedia: $url",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}