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

@Composable
fun AnimalCard(
    name: String,
    imageRes: Int,
    repostCount: Int,
    date: String,
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Bird",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillHeight
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightBeige)
                    .padding(16.dp)
            ) {
                Column {
                    androidx.compose.material3.Text(
                        text = name,
                        style = BoldGreen,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.repost_icon),
                                contentDescription = "Repost",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            androidx.compose.material3.Text(
                                text = repostCount.toString(),
                                style = InputMediumGreen,
                                fontSize = 14.sp
                            )
                        }
                        androidx.compose.material3.Text(
                            text = date,
                            style = InputMediumGreen,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(if (selected) DarkGreen else Color.Transparent)
            .border(BorderStroke(2.dp, DarkGreen), CircleShape)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = LightBeige,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun FilterCategoryPost(title: String, options: List<String>, selectedOption: MutableState<String?>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = ExtraBoldGreen,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { selectedOption.value = option }
            ) {
                CustomRadioButton(
                    selected = selectedOption.value == option,
                    onSelect = { selectedOption.value = option }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp),
                    style = InputMediumGreen,
                    fontSize = 18.sp
                )
            }
        }
    }
}





@Composable
fun BottomNavigationBar(
    sections: List<String>,
    activeSection: String,
    onSectionSelected: (String) -> Unit
) {
    BottomNavigation(
        backgroundColor = Brown,
        modifier = Modifier.height(80.dp)
    ) {
        sections.forEach { section ->
            val isActive = section == activeSection
            val iconRes = when (section) {
                "Статистика" -> if (isActive) R.drawable.statistics_beige else R.drawable.statistics_green
                "Публикации" -> if (isActive) R.drawable.pics_beige else R.drawable.pics_green
                "Новая запись" -> if (isActive) R.drawable.add_beige else R.drawable.add_green
                "Сообщения" -> if (isActive) R.drawable.message_beige else R.drawable.message_green
                else -> R.drawable.statistics_beige
            }

            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = section,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = section,
//                            style = InputMediumGreen,
//                            color = if (isActive) LightBeige else LightGreen,
//                            fontSize = 11.sp
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
                    }
                },
                selected = isActive,
                onClick = { onSectionSelected(section) },
                selectedContentColor = LightBeige,
                unselectedContentColor = LightGreen
            )
        }
    }
}

@Composable
fun MessagesSection(onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBeige)
//            .padding(top = 28.dp)
    ) {
        MessageItem(avatarRes = R.drawable.max_avatar, userName = "Максим", onClick = onItemClick)
        Divider(color = DarkBeige, thickness = 1.dp)
        MessageItem(avatarRes = R.drawable.lily_avatar, userName = "Лилия", onClick = onItemClick)
        Divider(color = DarkBeige, thickness = 1.dp)
        MessageItem(avatarRes = R.drawable.fernando_avatar, userName = "Федор", onClick = onItemClick)
    }
}

@Composable
fun MessageItem(avatarRes: Int, userName: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(70.dp)  // Устанавливаем размер изображения
                .clip(CircleShape),  // Обрезаем в круг
            contentScale = ContentScale.Crop  // Обрезаем изображение, чтобы оно заполнило круг
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = userName,
            style = ExtraBoldGreen,
            fontSize = 24.sp
        )
    }
}

@Composable
fun ImagePickerField(
    modifier: Modifier = Modifier,
    onImagesSelected: (List<Uri>) -> Unit // Коллбек для передачи выбранных изображений
) {
    val context = LocalContext.current
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.size <= 5) {
            selectedImages = uris
            onImagesSelected(uris)
        } else {
            // Показать сообщение об ошибке, если выбрано больше 5 изображений
            Toast.makeText(context, "Можно выбрать не более 5 изображений", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(LightGreen, RoundedCornerShape(20.dp))
            .clickable {
                launcher.launch("image/*") // Запуск выбора изображений
            },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImages.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_pics),
                    contentDescription = "Добавить фото",
                    tint = DarkGreen,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Добавить фото (до 5)",
                    style = InputMediumGreen,
                    color = DarkGreen
                )
            }
        } else {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(selectedImages.size) { index -> // Используем selectedImages.size
                    val uri = selectedImages[index]
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(uri)
                                .build()
                        ),
                        contentDescription = "Выбранное фото",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun NameField(query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(LightBeige, shape = RoundedCornerShape(25.dp)) // Цвет фона и закругленные углы
            .padding(start = 16.dp, end = 16.dp) // Отступы внутри поля поиска
    ) {
        // Поле для ввода текста
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = InputMediumGreen.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp) // Отступ от иконки
        )

        // Плейсхолдер
        if (query.isEmpty()) {
            androidx.compose.material3.Text(
                text = "Название животного",
                style = InputMediumGreen,
                modifier = Modifier.padding(start = 8.dp, top = 10.dp)
            )
        }

    }
}

@Composable
fun DescriptionField(query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Увеличенная высота для textarea
            .background(LightBeige, shape = RoundedCornerShape(25.dp))
            .padding(16.dp) // Отступы вокруг поля ввода
    ) {
        // Многострочное поле для ввода текста
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = InputMediumGreen.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), // Внутренние отступы
            maxLines = 6 // Ограничение количества строк
        )

        // Плейсхолдер
        if (query.isEmpty()) {
            androidx.compose.material3.Text(
                text = "Введите описание...",
                style = InputMediumGreen,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}


@Composable
fun FilterFieldPost() {
    val animalTypes = listOf("Птицы", "Млекопитающие", "Рептилии", "Земноводные")
    val sizes = listOf("Маленький", "Средний", "Большой")
    val locations = listOf("Парк", "Лес/Роща", "Водоем", "Двор/Крыша")

    // Сохраняем состояние с помощью remember
    val selectedAnimalType = remember { mutableStateOf<String?>(null) }
    val selectedSize = remember { mutableStateOf<String?>(null) }
    val selectedLocation = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FilterCategoryRadio(title = "Тип животных", options = animalTypes, selectedOption = selectedAnimalType)
        FilterCategoryRadio(title = "Размер", options = sizes, selectedOption = selectedSize)
        FilterCategoryRadio(title = "Место находки", options = locations, selectedOption = selectedLocation)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CustomRoundRadioButton(
    selected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(if (selected) DarkGreen else Color.Transparent)
            .border(BorderStroke(2.dp, DarkGreen), CircleShape)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = LightBeige,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun FilterCategoryRadio(title: String, options: List<String>, selectedOption: MutableState<String?>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = ExtraBoldGreen,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { selectedOption.value = option }
            ) {
                CustomRoundRadioButton(
                    selected = selectedOption.value == option,
                    onSelect = { selectedOption.value = option }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp),
                    style = InputMediumGreen,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun LocationPickerField(
    modifier: Modifier = Modifier,
    onLocationSelected: (String) -> Unit
) {
    var showMap by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("") }
    var selectedGeoPoint by remember { mutableStateOf<GeoPoint?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, androidx.preference.PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showMap = true
        } else {
            Toast.makeText(context, "Требуется разрешение для доступа к местоположению", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 17.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(LightBeige, shape = RoundedCornerShape(25.dp))
                .clickable {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        showMap = true
                    } else {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = selectedLocation.ifEmpty { "Выберите место на карте" },
                style = InputMediumGreen,
                color = DarkGreen
            )
        }
    }

    if (showMap) {
        AlertDialog(
            backgroundColor = LightBeige,
            shape = RoundedCornerShape(30.dp),
            onDismissRequest = {
                showMap = false
                selectedGeoPoint?.let {
                    val locationString = "%.6f, %.6f".format(it.latitude, it.longitude)
                    selectedLocation = locationString
                    onLocationSelected(locationString)
                }
            },
            title = { Text("Выберите место на карте", style = InputMediumGreen, fontSize = 20.sp) },
            text = {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .height(350.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .padding(8.dp)
                ) {
                    AndroidView(
                        factory = { context ->
                            MapView(context).apply {
                                setTileSource(TileSourceFactory.MAPNIK)
                                minZoomLevel = 3.0
                                maxZoomLevel = 18.0
                                setMultiTouchControls(true)

                                // Обработчик кликов на карту
                                overlays.add(object : Overlay() {
                                    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
                                        e ?: return false
                                        mapView ?: return false

                                        // Корректное преобразование координат
                                        val rect = mapView.projection.getScreenRect()
                                        val adjustedX = (e.x - rect.left).toInt()
                                        val adjustedY = (e.y - rect.top).toInt()

                                        val iGeoPoint = mapView.projection.fromPixels(adjustedX, adjustedY)
                                        val geoPoint = GeoPoint(iGeoPoint.latitude, iGeoPoint.longitude)

                                        overlays.removeIf { it is Marker }

                                        val marker = Marker(mapView).apply {
                                            position = geoPoint
                                            // Правильная установка якоря
                                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                            setIcon(ContextCompat.getDrawable(context, R.drawable.marker))
                                            infoWindow = null
                                        }

                                        overlays.add(marker)
                                        selectedGeoPoint = geoPoint
                                        invalidate()
                                        return true
                                    }
                                })

                                // Центрирование карты
                                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                                if (ContextCompat.checkSelfPermission(
                                        context, Manifest.permission.ACCESS_FINE_LOCATION
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                                        controller.setCenter(GeoPoint(it.latitude, it.longitude))
                                        controller.setZoom(15.0)
                                    } ?: run {
                                        controller.setCenter(GeoPoint(55.7558, 37.6176)) // Москва по умолчанию
                                        controller.setZoom(10.0)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(1.dp))
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showMap = false
                    selectedGeoPoint?.let {
                        val locationString = "%.6f, %.6f".format(it.latitude, it.longitude)
                        selectedLocation = locationString
                        onLocationSelected(locationString)
                    }
                }) {
                    Text("Сохранить", style = SemiBoldGreen)
                }
            }
        )
    }
}

