package Catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.LightBeige
import androidx.compose.ui.platform.LocalDensity
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.ExtraBoldLightBeige
import com.example.animals.R
import data.AnimalType
import data.animalList


@Composable
fun CatalogScreen(
    filters: Triple<Map<String, Boolean>, Map<String, Boolean>, Map<String, Boolean>>,
    onAboutClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAnimalCardClick: (AnimalType) -> Unit,
    onFilterClick: () -> Unit,
) {

    var searchQuery by remember { mutableStateOf("") }

    val filteredAnimalList = remember(searchQuery, filters) {
//        Log.d("CatalogScreen", "Фильтры в каталоге: " +
//                "Поиск: $searchQuery, " +
//                "Фильтры: $filters, "
//        )

        animalList.filter { animal ->
            animal.name.contains(searchQuery, ignoreCase = true) &&
                    (filters.first[animal.category] == true) &&
                    (filters.second[animal.size] == true) &&
                    (filters.third[animal.location] == true)
        }

    }

    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        // Верхняя панель
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topInset)
        ) {
            TopBar(onAboutClick, onProfileClick)
        }

        // Поле поиска
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 174.dp, start = 16.dp, end = 16.dp)
        ) {

            SearchBar(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery // Обновляем состояние при вводе текста
                }
                    )

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {onFilterClick()},
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.height(46.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen,
                    contentColor = LightBeige
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "Фильтры",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Фильтры", style = ExtraBoldLightBeige, fontSize = 17.sp)
            }


            Spacer(modifier = Modifier.height(26.dp))

            // Сетка карточек
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
                items(filteredAnimalList.size) { index ->
                    val animal = filteredAnimalList[index]
                    AnimalCardInCatalog(animal=animal, onCardClick = { onAnimalCardClick(animal) }
                    )
                }
            }
        }
    }
}

