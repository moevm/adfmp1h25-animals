package Profile.NewPost

import AnimalsFilter.categoryFilter
import AnimalsFilter.locationFilter
import AnimalsFilter.sizeFilter
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.animals.R
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.ExtraBoldGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import com.example.animals.ui.theme.LightGreen
import com.example.animals.ui.theme.SemiBoldGreen
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay



@Composable
fun ImagePickerField(
    modifier: Modifier = Modifier,
    onImagesSelected: (List<Uri>) -> Unit,
    clearImages: Boolean
) {
    val context = LocalContext.current
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    LaunchedEffect(clearImages) {
        if (clearImages) {
            selectedImages = emptyList()
            onImagesSelected(emptyList()) // Уведомляем родительский компонент об очистке
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    )

    { uris: List<Uri> ->
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
                items(selectedImages.size) { index ->
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
            .background(LightBeige, shape = RoundedCornerShape(25.dp))
            .padding(start = 16.dp, end = 16.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = InputMediumGreen.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp)
        )

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
            .height(150.dp)
            .background(LightBeige, shape = RoundedCornerShape(25.dp))
            .padding(16.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = InputMediumGreen.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            maxLines = 6
        )

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
                                            setIcon(
                                                ContextCompat.getDrawable(context,
                                                R.drawable.marker
                                            ))
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

@Composable
fun FilterFieldPost(
    selectedAnimalType: String?,
    onAnimalTypeSelected: (String) -> Unit,
    selectedSize: String?,
    onSizeSelected: (String) -> Unit,
    selectedLocation: String?,
    onLocationSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FilterCategoryRadio(
            title = "Тип животных",
            options = categoryFilter,
            selectedOption = selectedAnimalType,
            onOptionSelected = onAnimalTypeSelected)

        FilterCategoryRadio(title = "Размер",
            options = sizeFilter,
            selectedOption = selectedSize,
            onOptionSelected = onSizeSelected)

        FilterCategoryRadio(title = "Место находки",
            options = locationFilter,
            selectedOption = selectedLocation,
            onOptionSelected = onLocationSelected)

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
                contentDescription = "Выбран",
                tint = LightBeige,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun FilterCategoryRadio(title: String,
                        options: List<String>,
                        selectedOption: String?,
                        onOptionSelected: (String) -> Unit) {
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
                    .clickable { onOptionSelected(option) }
            ) {
                CustomRoundRadioButton(
                    selected = selectedOption == option,
                    onSelect = { onOptionSelected(option) }
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
