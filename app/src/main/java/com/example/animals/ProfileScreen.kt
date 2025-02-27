package com.example.animals

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
//import com.android.volley.toolbox.ImageRequest
import com.example.animals.ui.theme.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import android.Manifest
import android.view.MotionEvent
//import android.content.pm.PackageManager
//import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay


@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onRobinCardClick: () -> Unit,
    onChatClick: () -> Unit,
) {
    val sections = listOf("Статистика", "Публикации", "Новая запись", "Сообщения")
    var activeSection by remember { mutableStateOf(sections[3]) }
    var selectedTimeframe by remember { mutableStateOf("7 дней") }

    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    var nameQuery by remember { mutableStateOf("") }
    var descriptionQuery by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(top = topInset)) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = activeSection,
                                style = ExtraBoldBrown,
                                fontSize = 24.sp
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.left_back_brown),
                                contentDescription = "Назад",
                                tint = Color.Unspecified
                            )
                        }
                    },
                    backgroundColor = DarkBeige,
                    elevation = 0.dp
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                sections = sections,
                activeSection = activeSection,
                onSectionSelected = { activeSection = it }
            )
        },
        backgroundColor = DarkBeige
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (activeSection == "Статистика" || activeSection == "Публикации") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ProfileInfo() // Сюда добавляем блок профиля
                    if (activeSection == "Статистика") {
                        Spacer(modifier = Modifier.height(24.dp))
                        SegmentedPicker(
                            options = listOf("7 дней", "1 месяц", "1 год"),
                            selectedOption = selectedTimeframe,
                            onOptionSelected = { selectedTimeframe = it }
                        )
                        StatisticsContent(timeframe = selectedTimeframe)
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
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
                            items(3) {
                                AnimalCard(
                                    name = "Малиновка",
                                    imageRes = R.drawable.robin1,
                                    repostCount = 10,
                                    date = "14.02.2025",
                                    onClick = onRobinCardClick
                                )
                            }
                        }
                    }
                }
            } else if (activeSection == "Сообщения") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ProfileInfo() // Добавляем блок профиля в раздел "Сообщения"
                    Spacer(modifier = Modifier.height(30.dp))
                    SearchBarUsers(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    // Здесь будет контент раздела "Сообщения"
                    MessagesSection(onItemClick = onChatClick)
                    // Место для списка сообщений или других элементов
                }
            }  else if (activeSection == "Новая запись") {
                var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
                var selectedLocation by remember { mutableStateOf("") }

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
                        }
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
                        text = "Задайте параметры ",
                        style = ExtraBoldGreen,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FilterFieldPost()

                    LocationPickerField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        onLocationSelected = { location ->
                            selectedLocation = location
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.Button(
                        onClick = {  },
                        modifier = Modifier
                            .padding(17.dp)
                            .fillMaxWidth(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = LightGreen
                        )
                    ) {
                        androidx.compose.material3.Text(
                            text = "Опубликовать",
                            style = ButtonsExtraBold,
                            fontWeight = FontWeight.ExtraBold,
                            color = DarkGreen,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
            } else {


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Контент для раздела: $activeSection",
                        fontSize = 18.sp,
                        color = DarkGreen
                    )
                }
            }
        }
    }
}

@Composable
fun SegmentedPicker(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(50))
            .background(LightGreen),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option == selectedOption
            val isFirst = index == 0
            val isLast = index == options.lastIndex

            Button(
                onClick = { onOptionSelected(option) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSelected) LightBeige else Color.Transparent,
                    contentColor = if (isSelected) LightBeige else DarkGreen
                ),
                shape = RoundedCornerShape(50),
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = if (isSelected && isFirst) 4.dp else 0.dp,
                        end = if (isSelected && isLast) 4.dp else 0.dp
                    )
            ) {
                Text(
                    text = option,
                    style = SemiBoldGreen,
                )
            }
        }
    }
}

@Composable
fun StatisticsContent(timeframe: String) {
    val stats = when (timeframe) {
        "7 дней" -> mapOf(
            "Количество публикаций" to 4,
            "Количество млекопитающих" to 3,
            "Количество птиц" to 1,
            "Количество рептилий" to 0,
            "Количество земноводных" to 0
        )
        "1 месяц" -> mapOf(
            "Количество публикаций" to 10,
            "Количество млекопитающих" to 13,
            "Количество птиц" to 7,
            "Количество рептилий" to 5,
            "Количество земноводных" to 5
        )
        "1 год" -> mapOf(
            "Количество публикаций" to 100,
            "Количество млекопитающих" to 35,
            "Количество птиц" to 50,
            "Количество рептилий" to 10,
            "Количество земноводных" to 10
        )
        else -> emptyMap()
    }

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

@Composable
fun ProfileInfo() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Запуск выбора изображения из галереи
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        val imagePainter: Painter = if (selectedImageUri != null) {
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(selectedImageUri)
                    .build()
            )
        } else {
            painterResource(id = R.drawable.profile_avatar)
        }

        Image(
            painter = imagePainter,
            contentDescription = "Фото профиля",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
                .clickable {
                    // Открыть галерею при нажатии
                    launcher.launch("image/*")
                },
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Алексей",
            style = ExtraBoldGreen,
            fontSize = 24.sp
        )
    }
}