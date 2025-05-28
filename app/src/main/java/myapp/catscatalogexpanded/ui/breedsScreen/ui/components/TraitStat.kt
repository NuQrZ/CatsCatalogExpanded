package myapp.catscatalogexpanded.ui.breedsScreen.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TraitStat(label: String, value: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.SemiBold)
        LinearProgressIndicator(
            progress = { value / 5f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}