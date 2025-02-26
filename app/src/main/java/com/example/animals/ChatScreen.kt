package com.example.animals // Замените на ваш пакет

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.ExtraBoldGreen
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.zIndex
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import com.example.animals.ui.theme.SemiBoldGreen
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ChatScreen(onBack: () -> Unit = {}) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember {
        listOf(
            Message("Какие красивые фотографии!", false, "10.02.2025"),
            Message("Спасибо!!", true, "10.02.2025"),
            SharedMessage("Малиновка", R.drawable.robin1, "10.02.2025", 10, true),
            SharedMessage("Малиновка", R.drawable.robin1, "10.02.2025", 10, false),
            Message("Спасибо!!", true, "10.02.2025"),
            Message("Спасибо!!", true, "10.02.2025"),
            Message("Спасибо!!", true, "10.02.2025"),
        )
    }
    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) { insets.getTop(this).toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topInset)
        ) {
            TopBarChat(onBack = onBack)
            ChatMessagesList(messages = messages) // Теперь займет оставшееся пространство

        }
    }
}

@Composable
fun TopBarChat(onBack: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .zIndex(1f),
        color = DarkBeige,
        shadowElevation = 16.dp,
        tonalElevation = 0.dp,
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка назад
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp) // Увеличиваем размер кнопки
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.left_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp) // Увеличиваем размер иконки
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Круглый аватар
            Image(
                painter = painterResource(id = R.drawable.max_avatar), // Замените на вашу картинку
                contentDescription = "Max Avatar",
                modifier = Modifier
                    .size(64.dp)  // Устанавливаем размер изображения
                    .clip(CircleShape),  // Обрезаем в круг
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Текст с именем
            Text(
                text = "Максим",
                style = ExtraBoldGreen,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun InputField(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        color = LightBeige
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = messageText,
                onValueChange = onMessageChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) keyboardController?.show()
                    },
                placeholder = {
                    if (messageText.isEmpty()) {
                        Text(
                            text = "Сообщение",
                            style = InputMediumGreen.copy(fontSize = 18.sp)
                        )
                    }
                },
                textStyle = InputMediumGreen.copy(
                    fontSize = 18.sp,
                    color = DarkGreen
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            IconButton(
                onClick = onSendClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "Отправить",
                    tint = DarkGreen
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
}

// Основной класс сообщения
open class Message(
    val text: String,
    val isFromMe: Boolean,
    val timestamp: String
)

// Класс для сообщения с постом (shared message)
data class SharedMessage(
    val postTitle: String,
    val postImage: Int,
    val sharedTimestamp: String = "",
    val repostsCount: Int,
    val isFromMeShared: Boolean,
) : Message(text = postTitle, isFromMe = isFromMeShared, timestamp = sharedTimestamp)


@Composable
fun ChatBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    val alignment = if (message.isFromMe) Alignment.End else Alignment.Start
    val background = if (message.isFromMe) Color(0xFFF9EBC7) else Color(0xFFF5E5BD)
    val shape = RoundedCornerShape(16.dp)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.CenterEnd else Alignment.CenterStart)
                .widthIn(max = screenWidth * 0.75f) // Ограничиваем ширину 75% экрана
                .shadow(4.dp, shape = shape)
                .background(background, shape)
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                style = InputMediumGreen,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.timestamp,
                style = InputMediumGreen,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun SharedMessageCard(
    sharedMessage: SharedMessage,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.45f) // Уменьшаем ширину карточки
                .align(if (sharedMessage.isFromMeShared) Alignment.CenterEnd else Alignment.CenterStart)
                .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Отображаем изображение поста
                Image(
                    painter = painterResource(id = sharedMessage.postImage),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                // Контейнер для информации о посте
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (sharedMessage.isFromMeShared) Color(0xFFF9EBC7) else Color(0xFFF5E5BD)) // Можно заменить на другой цвет фона
                        .padding(16.dp)
                ) {
                    Column {
                        // Заголовок поста
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
                                    contentDescription = "Repost",
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
                                text = sharedMessage.timestamp,
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

@Composable
fun ChatMessagesList(messages: List<Message>) {
    var messageText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Теперь weight применяется корректно в Column
            reverseLayout = false, // Порядок сверху вниз
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                when (message) {
                    is SharedMessage -> SharedMessageCard(sharedMessage = message)
                    else -> ChatBubble(message = message)
                }
            }
        }

        InputField( // Переместили внутрь Column
            messageText = messageText,
            onMessageChange = { messageText = it },
            onSendClick = { /*...*/ },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .zIndex(1f)
        )
    }
}


