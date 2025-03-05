package Chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import coil.compose.rememberAsyncImagePainter
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.SemiBoldGreen
import com.example.animals.R
import data.AnimalType
import data.BaseMessageType
import data.ImageSource
import data.MessageType
import data.SharedMessageType
import data.animalList
import utils.getCurrentDate
import utils.getCurrentTime


@RequiresApi(Build.VERSION_CODES.O)
fun addSharedMessage(
    messages: MutableList<BaseMessageType>,
    animal: AnimalType,
) {
    val newSharedMessage = SharedMessageType(
        postTitle = animal.name,
        postImage = animal.mainImage,
        date = getCurrentDate(),
        time = getCurrentTime(),
        repostsCount = animal.repostCount,
        isFromMeShared = true,
    )
    messages.add(newSharedMessage)
}

@Composable
fun SharedMessageCard(
    sharedMessage: SharedMessageType,
    modifier: Modifier = Modifier,
    onSharedMessageClick: (animal: AnimalType) -> Unit
) {
    val foundAnimal = animalList.find { it.name == sharedMessage.postTitle }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Log.d("SharedMessageCard", "Найденное животное: ${sharedMessage}, $foundAnimal")
                foundAnimal?.let { animal ->
                    onSharedMessageClick(animal)
                }
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .align(if (sharedMessage.isFromMeShared) Alignment.CenterEnd else Alignment.CenterStart)
                .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // изображение поста
                when (sharedMessage.postImage) {
                    is ImageSource.Drawable -> {
                        Image(
                            painter = painterResource(id = sharedMessage.postImage.id),
                            contentDescription = "Животное",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    is ImageSource.UriSource -> {
                        Image(
                            painter = rememberAsyncImagePainter(sharedMessage.postImage.uri),
                            contentDescription = "Животное",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                // Контейнер для информации о посте
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (sharedMessage.isFromMeShared) Color(0xFFF9EBC7) else Color(0xFFF5E5BD))
                        .padding(16.dp)

                ) {
                    Column {
                        Text(
                            text = sharedMessage.postTitle,
                            style = SemiBoldGreen,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Дата и имя пользователя, который поделился постом
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
                                    text = sharedMessage.repostsCount.toString(),
                                    style = InputMediumGreen,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = "${sharedMessage.date} ${sharedMessage.time}",
                                style = InputMediumGreen,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

