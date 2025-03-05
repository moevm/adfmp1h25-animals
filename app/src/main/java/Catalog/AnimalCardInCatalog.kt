package Catalog

import AnimalCard.FullScreenImageSlider
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.animals.ui.theme.BoldGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import com.example.animals.R
import data.AnimalType
import data.ImageSource
import androidx.compose.foundation.*
import androidx.compose.ui.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.animals.ui.theme.*

@Composable
fun AnimalCardInCatalog(animal: AnimalType, onCardClick: () -> Unit) {
    var showFullScreenImage by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf(0) }


    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onCardClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box {
                when (animal.mainImage) {
                    is ImageSource.Drawable -> {
                        Image(
                            painter = painterResource(id = animal.mainImage.id),
                            contentDescription = "Животное",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                showFullScreenImage = true
                                selectedImageIndex = 0
                            },
                        contentScale = ContentScale.Crop
                        )
                    }
                    is ImageSource.UriSource -> {
                        Image(
                            painter = rememberAsyncImagePainter(animal.mainImage.uri),
                            contentDescription = "Животное",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                    showFullScreenImage = true
                                    selectedImageIndex = 0
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Отображение количества фото в правом верхнем углу изображения
                if (animal.images.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                                shape = CircleShape)
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.pics_beige),
                                contentDescription = "Фото",
                                modifier = Modifier.size(12.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${animal.images.size}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightBeige)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = animal.name,
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
                                contentDescription = "Репост",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = animal.repostCount.toString(),
                                style = InputMediumGreen,
                                fontSize = 14.sp
                            )
                        }
                        Column {
                            Text(
                                text = animal.date,
                                style = InputMediumGreen,
                                fontSize = 14.sp
                            )
                            Text(
                                text = animal.time,
                                style = InputMediumGreen,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            }
        }

    if (showFullScreenImage) {
        Dialog(
            onDismissRequest = { showFullScreenImage = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            FullScreenImageSlider(
                images = animal.images,
                initialPage = selectedImageIndex,
                onDismiss = { showFullScreenImage = false }
            )
        }
    }
}

