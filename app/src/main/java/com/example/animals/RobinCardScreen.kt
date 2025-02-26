package com.example.animals

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*
import com.google.accompanist.pager.*
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinCardScreen(onBackClick: () -> Unit, onProfileClick: () -> Unit) {
    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    var searchQuery by remember { mutableStateOf("") }

    // Состояние для управления видимостью модального окна
    var showBottomSheet by remember { mutableStateOf(false) }
    val view = LocalView.current
    LaunchedEffect(showBottomSheet) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            it.statusBarColor = LightGreen.toArgb() // Установите цвет статус-бара
            WindowInsetsControllerCompat(it, view).isAppearanceLightStatusBars = true // Светлый текст статус-бара
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topInset)
                .verticalScroll(rememberScrollState())
        ) {
            TopBarCard(onBackClick, onProfileClick)
            ImageSlider()
            BirdInfo(onShareClick = { showBottomSheet = true })
        }

        // Модальное окно
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = rememberModalBottomSheetState(),
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent // Установите цвет фона модального окна
//                    .background(LightGreen)
            ) {
                Box (
                    modifier = Modifier.fillMaxWidth()
                        .background(LightGreen)
//                        .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                )
                {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75f) // Примерно 45% высоты экрана
//                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .background(Color(0xFF9AA57D))

                            ){
                                Row(
                                    modifier = Modifier.fillMaxWidth()
//                                    .background(Color(0xFF9AA57D)),
                                        .padding(vertical = 5.dp, horizontal = 16.dp),
//                                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Поделиться",
                                        style = BoldGreen,
                                        fontSize = 24.sp
                                    )
                                    IconButton(onClick = { showBottomSheet = false }) {
                                        Image(
                                            painter = painterResource(id = R.drawable.close_share),
                                            contentDescription = "Закрыть",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                            Column (
                                modifier = Modifier.fillMaxWidth()
                                    .padding(16.dp),
                            ){
                                SearchBarUsers(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it }
                                )

                                Spacer(modifier = Modifier.height(26.dp))

                                ShareItem(
                                    userAvatar = R.drawable.max_avatar, // Аватарка пользователя
                                    userName = "Максим", // Имя пользователя
                                    onSendClick = { /* Обработчик нажатия на кнопку "Отправить" */ }
                                )

                                ShareItem(
                                    userAvatar = R.drawable.lily_avatar, // Аватарка пользователя
                                    userName = "Лилия", // Имя пользователя
                                    onSendClick = { /* Обработчик нажатия на кнопку "Отправить" */ }
                                )
                                ShareItem(
                                    userAvatar = R.drawable.fernando_avatar, // Аватарка пользователя
                                    userName = "Федор", // Имя пользователя
                                    onSendClick = { /* Обработчик нажатия на кнопку "Отправить" */ }
                                )

                                ShareItem(
                                    userAvatar = R.drawable.alex_avatar, // Аватарка пользователя
                                    userName = "Александра", // Имя пользователя
                                    onSendClick = { /* Обработчик нажатия на кнопку "Отправить" */ }
                                )
                            }

                        }
                    }
                }


            }
        }
    }
}

@Composable
fun TopBarCard(onBackClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 7.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                Log.d("RobinCardScreen", "Back button clicked")
                onBackClick()
            },
            modifier = Modifier.size(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.left_back),
                contentDescription = "Назад",
                modifier = Modifier.size(30.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logo_catalog),
            contentDescription = "Логотип",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )

        IconButton(
            onClick = {
                Log.d("RobinCardScreen", "Profile button clicked")
                onProfileClick()
            },
            modifier = Modifier.size(70.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_avatar),
                contentDescription = "Профиль",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun MapShortcut() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp) // Отступы по бокам
    ) {
        AndroidView(
            factory = { context ->
                Configuration.getInstance().load(
                    context,
                    context.getSharedPreferences("osm_prefs", android.content.Context.MODE_PRIVATE)
                )
                MapView(context).apply {
                    setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(12.0)
                    controller.setCenter(GeoPoint(55.751244, 37.618423)) // Координаты Москвы

                    // Ограничение области масштабирования внутри видимой зоны
                    this.setScrollableAreaLimitDouble(
                        org.osmdroid.util.BoundingBox(
                            55.85, 37.75, // Северо-западный угол
                            55.65, 37.49  // Юго-восточный угол
                        )
                    )

                    // Ограничения на масштаб
                    this.minZoomLevel = 3.0
                    this.maxZoomLevel = 18.0
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
//                .clip(CircleShape) // Опционально, если хотите сделать закругленные углы
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider() {
    val imageList = listOf(
        R.drawable.robin1,
        R.drawable.robin2,
        R.drawable.robin3
    )

    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(top = 16.dp)
        ) { page ->
            val actualPage = page % imageList.size
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = imageList[actualPage]),
                    contentDescription = "Слайдер изображение",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imageList.size) { index ->
                val isActive = index == (pagerState.currentPage % imageList.size)
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(if (isActive) DarkGreen else LightGreen)
                )
            }
        }
    }
}

@Composable
fun BirdInfo(onShareClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Малиновка",
            style = ExtraBoldGreen,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Небольшая заметка от автора о животном",
            style = InputMediumGreen,
            lineHeight = 30.sp,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = "Автор публикации:", value = "Алексей")
        InfoRow(label = "Тип животного:", value = "Птица")
        InfoRow(label = "Размер животного:", value = "Маленький")
        InfoRow(label = "Место встречи:", value = "Парк")
        Spacer(modifier = Modifier.height(16.dp))
        MapShortcut()
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = "Дата встречи:", value = "14.02.2024")

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

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = BoldGreen,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = InputMediumGreen,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun SearchBarUsers(query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = InputMediumGreen.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBeige, shape = RoundedCornerShape(25.dp))
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "Search",
                        modifier = Modifier.size(20.dp)
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Поиск пользователя",
                                style = InputMediumGreen.copy(color = DarkGreen.copy(alpha = 0.65f)),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}
