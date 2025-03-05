package AnimalCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*
import com.example.animals.R
import data.AnimalType


@Composable
fun AnimalInfo(animal: AnimalType,
               onShareClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = animal.name,
            style = ExtraBoldGreen,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = animal.description,
            style = InputMediumGreen,
            lineHeight = 30.sp,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = "Автор публикации:", value = animal.author)
        InfoRow(label = "Тип животного:", value = animal.category)
        InfoRow(label = "Размер животного:", value = animal.size)
        InfoRow(label = "Место встречи:", value = animal.location)
        Spacer(modifier = Modifier.height(16.dp))
        MapShortcut(coordinates = animal.geoLocation)
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = "Дата встречи:", value = "${animal.date} ${animal.time}")

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Поделиться:",
                style = BoldGreen,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onShareClick) {
                Image(
                    painter = painterResource(id = R.drawable.share_icon),
                    contentDescription = "Поделиться",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

