package Chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.zIndex
import com.example.animals.R
import com.example.animals.ui.theme.*
import data.AnimalType
import data.MessageType
import data.SharedMessageType
import data.UsersData
import utils.getCurrentDate
import utils.getCurrentTime
import androidx.compose.runtime.snapshots.SnapshotStateList

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
                contentDescription = "User Avatar",
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
    var messageText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
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
            BasicTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) keyboardController?.show()
                    },
                textStyle = InputMediumGreen.copy(
                    fontSize = 18.sp,
                    color = DarkGreen
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (messageText.text.isEmpty()) {
                            Text(
                                text = "Сообщение",
                                style = InputMediumGreen.copy(
                                    fontSize = 18.sp,
                                    color = DarkGreen.copy(alpha = 0.6f)
                                )
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true
            )

            IconButton(
                onClick = {
                    val text = messageText.text.trim()
                    if (text.isNotEmpty()) {
                        onSendClick(text)
                        messageText = TextFieldValue("")
                    }
                },
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
    val user = UsersData.users.find { it.name == name }
    val messages = user?.messages ?: remember { mutableStateListOf() }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            lazyListState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                when (message) {
                    is SharedMessageType -> SharedMessageCard(
                        sharedMessage = message,
                        onSharedMessageClick = onSharedMessageClick
                    )
                    is MessageType -> MessageInChat(message = message)
                }
            }
        }

        InputField(
            onSendClick = { messageText ->
                if (messageText.isNotEmpty()) {
                    val newMessage = MessageType(
                        text = messageText,
                        isFromMe = true,
                        date = getCurrentDate(),
                        time = getCurrentTime()
                    )
                    user?.messages?.add(newMessage)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .zIndex(1f)
        )
    }
}