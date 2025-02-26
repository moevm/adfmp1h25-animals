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
