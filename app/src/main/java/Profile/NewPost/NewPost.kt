package Profile.NewPost

import com.example.animals.ui.theme.*
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.R
import data.AnimalType
import data.ImageSource
import data.animalList
import utils.getCurrentDate
import utils.getCurrentTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewPost(
) {
    var nameQuery by remember { mutableStateOf("") }
    var descriptionQuery by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var selectedLocation by remember { mutableStateOf<String?>(null) }
    var selectedAnimalType by remember { mutableStateOf<String?>(null) }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var selectedMapLocation by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var shouldClearImages by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImagePickerField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onImagesSelected = { uris ->
                selectedImages = uris
            },
            clearImages = shouldClearImages
        )

        Spacer(modifier = Modifier.height(16.dp))

        NameField(
            query = nameQuery,
            onQueryChange = { nameQuery = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DescriptionField(
            query = descriptionQuery,
            onQueryChange = { descriptionQuery = it }
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Задайте параметры",
            style = ExtraBoldGreen,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        FilterFieldPost(
            selectedAnimalType = selectedAnimalType,
            onAnimalTypeSelected = { selectedAnimalType = it },
            selectedSize = selectedSize,
            onSizeSelected = { selectedSize = it },
            selectedLocation = selectedLocation,
            onLocationSelected = { selectedLocation = it }
        )

        LocationPickerField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onLocationSelected = { location ->
                selectedMapLocation = location
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                when {
                    selectedImages.isEmpty() -> {
                        errorMessage = "Добавьте фото"
                    }

                    nameQuery.isNullOrBlank() -> {
                        errorMessage = "Введите название"
                    }

                    descriptionQuery.isNullOrBlank() -> {
                        errorMessage = "Введите описание"
                    }

                    selectedAnimalType == null -> {
                        errorMessage = "Выберите тип животного"
                    }

                    selectedSize == null -> {
                        errorMessage = "Выберите размер"
                    }

                    selectedMapLocation == null -> {
                        errorMessage = "Выберите место находки"
                    }

                    else -> {
                        val parts = selectedMapLocation!!.split(",").map { it.trim() } // Убираем лишние пробелы
                        val correctSelectedImages = selectedImages.map { uri -> ImageSource.UriSource(uri) }

                        val newAnimal = AnimalType(
                            name = nameQuery,
                            mainImage = correctSelectedImages[0],
                            repostCount = 0,
                            date = getCurrentDate(),
                            time = getCurrentTime(),
                            author = "Алексей",
                            location = selectedLocation!!,
                            category = selectedAnimalType!!,
                            geoLocation = listOf(parts[0].toDouble(), parts[1].toDouble()),
                            size = selectedSize!!,
                            description = descriptionQuery,
                            images = correctSelectedImages
                        )

                        Log.d("NewPost", "${selectedImages}")

                        animalList.add(newAnimal)
                        errorMessage = "Животное успешно добавлено!"
                        nameQuery =""
                        descriptionQuery =""
                        selectedImages = emptyList()
                        selectedLocation =null
                        selectedAnimalType =null
                        selectedSize =null
                        selectedMapLocation =null
                        shouldClearImages = true
                    }
                }
            },
            modifier = Modifier
                .padding(17.dp)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(10.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = LightGreen
            )
        ) {
            Text(
                text = "Опубликовать",
                style = ButtonsExtraBold,
                fontWeight = FontWeight.ExtraBold,
                color = DarkGreen,
                modifier = Modifier.padding(6.dp)
            )
        }

        errorMessage?.let { message ->
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            errorMessage = null
        }
    }
}
