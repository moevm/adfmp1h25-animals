package AnimalCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.rememberAsyncImagePainter
import com.example.animals.R
import data.ImageSource


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
//                Log.d("RobinCardScreen", "Back button clicked")
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
//                Log.d("RobinCardScreen", "Profile button clicked")
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
fun MapShortcut(coordinates: List<Double>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp)
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
                    controller.setCenter(GeoPoint(coordinates[0], coordinates[1])) // Координаты Москвы

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
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FullScreenImageSlider(
    images: List<ImageSource>,
    initialPage: Int,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initialPage)

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val image = images[page]

                when (image) {
                    is ImageSource.Drawable ->
                        Image(
                            painter = painterResource(id = image.id),
                            contentDescription = "Изображение во весь экран",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )

                    is ImageSource.UriSource ->
                        Image(
                            painter = rememberAsyncImagePainter(image.uri),
                            contentDescription = "Изображение во весь экран",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            ) {
                Icon(painterResource(R.drawable.close_share), contentDescription = "Закрыть")
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(imageList: List<ImageSource>,
                onImageClick: (Int) -> Unit) {

    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2
    )

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
            val image = imageList[actualPage]

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .clickable { onImageClick(actualPage)}
            ) {
                when (image) {
                    is ImageSource.Drawable -> {
                        Image(
                            painter = painterResource(id = image.id),
                            contentDescription = "Изображение животного",
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    is ImageSource.UriSource -> {
                        Image(
                            painter = rememberAsyncImagePainter(image.uri),
                            contentDescription = "Изображение животного",
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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
                        contentDescription = "Поиск",
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

@Composable
fun ShareItem(
    userAvatar: Int,
    userName: String,
    onSendClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = userAvatar),
                contentDescription = "Пользователь",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = userName,
                style = BoldGreen,
                fontSize = 18.sp
            )
        }

        Button(
            onClick = onSendClick,
            modifier = Modifier
                .height(36.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Brown,
                contentColor = LightBeige
            )
        ) {
            Text(
                text = "Отправить",
                style = NormalLightBeige,
                fontSize = 16.sp
            )
        }
    }
}
