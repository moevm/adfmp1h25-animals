package Chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.toMutableStateList
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.zIndex
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import androidx.compose.ui.platform.LocalConfiguration
import com.example.animals.R
import data.AnimalType
import data.MessageType
import data.SharedMessageType
import data.UsersData
import utils.getCurrentDate
import utils.getCurrentTime


@Composable
fun TopBarChat(onBack: () -> Unit, name: String) {
    val avatar = remember(name) {
        UsersData.users.find { it.name == name }?.avatar ?: R.drawable.empty_avatar
    }


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
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.left_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = avatar),
                contentDescription = "Max Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = name,
                style = ExtraBoldGreen,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun InputField(
    onSendClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }
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
                onValueChange = { messageText = it
                    Log.d("Chat", "Измененное сообщение: $messageText")},
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
                onClick = {
                    Log.d("Input", "Отправленное сообщение: $messageText")
                    onSendClick(messageText)
                    messageText=""},
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


@Composable
fun MessageInChat(
    message: MessageType,
    modifier: Modifier = Modifier
) {
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
                .widthIn(max = screenWidth * 0.75f)
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
                text = "${message.date} ${message.time}",
                style = InputMediumGreen,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessagesList(name: String, onSharedMessageClick: (animal: AnimalType) -> Unit) {
    val messages = UsersData.users.find { it.name == name }?.messages ?: mutableListOf()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = false, // Порядок сверху вниз
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                when (message) {
                    is SharedMessageType -> SharedMessageCard(
                        sharedMessage = message,
                        onSharedMessageClick = {animal -> onSharedMessageClick(animal)})
                    is MessageType -> MessageInChat(message = message)
                }
            }
        }

        InputField(
            onSendClick = {message ->
                if (message.isNotEmpty()) {
                    val newMessage = MessageType(message, true, getCurrentDate(), getCurrentTime())
                    UsersData.users.find { it.name == name }?.messages?.add(newMessage)
                    Log.d("Chat", "messageText после отправки: $message")
                } },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .zIndex(1f)
        )
    }
}




