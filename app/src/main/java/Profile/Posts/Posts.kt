package Profile.Posts

import Catalog.AnimalCardInCatalog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.AnimalType
import data.animalList

@Composable
fun Posts(
    authorName: String,
    onAnimalCardClick: (AnimalType) -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = 0.dp,
            start = 0.dp,
            end = 0.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        val filteredAnimals = animalList.filter { it.author == authorName }
        items(filteredAnimals.size) { index ->
            val animal = filteredAnimals[index]
            AnimalCardInCatalog(
                animal = animal,
                onCardClick = { onAnimalCardClick(animal) }
            )
        }
    }
}