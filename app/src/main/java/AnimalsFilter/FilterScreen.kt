package AnimalsFilter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animals.ui.theme.Brown
import com.example.animals.ui.theme.ButtonsExtraBold
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.ExtraBoldGreen
import com.example.animals.ui.theme.LightBeige
import com.example.animals.ui.theme.NormalLightBeige
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

val categoryFilter = listOf("Птицы", "Млекопитающие", "Рептилии", "Земноводные")
val sizeFilter = listOf("Маленький", "Средний", "Большой")
val locationFilter = listOf("Парк", "Лес/Роща", "Водоем", "Двор/Крыша")

@Composable
fun FilterScreen(
    currentAnimalFilters: Map<String, Boolean>,
    currentSizeFilters: Map<String, Boolean>,
    currentLocationFilters: Map<String, Boolean>,
    onCancel: () -> Unit = {},
    onApply: (Map<String, Boolean>, Map<String, Boolean>, Map<String, Boolean>) -> Unit
) {

    val animalTypeState = remember { mutableStateMapOf<String, Boolean>().apply { categoryFilter.forEach { put(it, currentAnimalFilters[it] ?: false) } } }
    val sizeState = remember { mutableStateMapOf<String, Boolean>().apply { sizeFilter.forEach { put(it, currentSizeFilters[it] ?: false) } } }
    val locationState = remember { mutableStateMapOf<String, Boolean>().apply { locationFilter.forEach { put(it, currentLocationFilters[it] ?: false) } } }

    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        // Используем LazyColumn для добавления прокрутки
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .padding(top = topInset)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(48.dp))
                        Text(
                            text = "Фильтры",
                            style = ExtraBoldGreen,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        TextButton(
                            onClick = {
                                animalTypeState.keys.forEach { animalTypeState[it] = false }
                                sizeState.keys.forEach { sizeState[it] = false }
                                locationState.keys.forEach { locationState[it] = false }
                                onCancel()
                            },
                            modifier = Modifier.height(40.dp),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Brown,
                                contentColor = LightBeige
                            )
                        ) {
                            Text("Отмена", style = NormalLightBeige)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                FilterCategory(title = "Тип животных", options = animalTypeState)
            }

            item {
                FilterCategory(title = "Размер", options = sizeState)
            }

            item {
                FilterCategory(title = "Место находки", options = locationState)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        onApply(animalTypeState.toMap(), sizeState.toMap(), locationState.toMap())
                    },
                    modifier = Modifier
                        .padding(17.dp)
                        .fillMaxWidth(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text(
                        text = "Применить",
                        style = ButtonsExtraBold,
                        fontWeight = FontWeight.ExtraBold,
                        color = LightBeige,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}
