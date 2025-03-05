package Profile.Statistics

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import data.animalList
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Statistics() {
    var selectedTimeframe by remember { mutableStateOf("7 дней") }
    var showNotification by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(24.dp))
    SegmentedPicker(
        options = listOf("7 дней", "1 месяц", "1 год"),
        selectedOption = selectedTimeframe,
        onOptionSelected = { selectedTimeframe = it }
    )
    StatisticsContent(timeframe = selectedTimeframe, onEmptyData = { showNotification = true })
    if (showNotification) {
        Snackbar(
            action = {
                TextButton(onClick = { showNotification = false }) {
                    Text("Закрыть", color = Color.White)
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Нет данных за выбранный период.")
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun StatisticsContent(timeframe: String, onEmptyData: () -> Unit) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val currentDate = LocalDate.now()

    fun isWithinTimeframe(postDate: String, days: Long): Boolean {
        val parsedDate = LocalDate.parse(postDate, dateFormatter)
        return ChronoUnit.DAYS.between(parsedDate, currentDate) <= days
    }

    val days = when (timeframe) {
        "7 дней" -> 7L
        "1 месяц" -> 30L
        "1 год" -> 365L
        else -> 0L
    }

    val filteredList = animalList.filter { it.author == "Алексей" && isWithinTimeframe(it.date, days) }

    if (filteredList.isEmpty()) {
        onEmptyData()
    }

    val totalPublications = filteredList.size
    val mammalCount = filteredList.count { it.category == "Млекопитающие" }
    val birdCount = filteredList.count { it.category == "Птицы" }
    val reptileCount = filteredList.count { it.category == "Рептилии" }
    val amphibianCount = filteredList.count { it.category == "Земноводные" }

    val stats = mapOf(
        "Количество публикаций" to totalPublications,
        "Количество млекопитающих" to mammalCount,
        "Количество птиц" to birdCount,
        "Количество рептилий" to reptileCount,
        "Количество земноводных" to amphibianCount
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stats.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = SemiBoldGreen,
                    fontSize = 18.sp
                )
                Text(
                    text = "$value",
                    style = SemiBoldGreen,
                    fontSize = 18.sp
                )
            }
        }
    }
}
