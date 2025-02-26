package com.example.animals

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.ExtraBoldGreen
import com.example.animals.ui.theme.InputMediumGreen

@Composable
fun AboutScreen(onBackClick: () -> Unit, onProfileClick: () -> Unit) {
    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topInset)
        ) {
            TopBarAbout(onBackClick, onProfileClick)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp, start = 24.dp, end = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Разработчики:\n",
                style = ExtraBoldGreen,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 50.dp, bottom = 5.dp)
            )
            Text(
                text = "Новак Полина\n",
                style = InputMediumGreen,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = "Хабибуллина Алина\n",
                style = InputMediumGreen,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = "Хулап Олеся\n",
                style = InputMediumGreen,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun TopBarAbout(onBackClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Немного увеличил высоту TopBar
            .padding(start = 5.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                Log.d("CatalogScreen", "Info button clicked")
                onBackClick()
            },
            modifier = Modifier.size(50.dp) // Увеличиваем размер кнопки
        ) {
            Image(
                painter = painterResource(id = R.drawable.left_back),
                contentDescription = "Назад",
                modifier = Modifier.size(30.dp) // Увеличиваем саму иконку
            )
        }

        Text(
            text = "About",
            style = ExtraBoldGreen,
            modifier = Modifier.padding(16.dp)
        )

        IconButton(
            onClick = {
                Log.d("CatalogScreen", "Profile button clicked")
                onProfileClick()
            },
            modifier = Modifier.size(70.dp) // Увеличиваем размер кнопки
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_avatar),
                contentDescription = "Профиль",
                modifier = Modifier
                    .size(60.dp) // Увеличиваем саму иконку
                    .clip(CircleShape)
            )
        }
    }
}
