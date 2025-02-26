package com.example.animals

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.BoldGreen
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import androidx.compose.ui.platform.LocalDensity
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.ExtraBoldLightBeige
//import androidx.compose.foundation.onFocusChanged
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import com.example.animals.ui.theme.Brown
import com.example.animals.ui.theme.LightGreen
import com.example.animals.ui.theme.NormalLightBeige

@Composable
fun CatalogScreen(
    onAboutClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRobinCardClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        // Верхняя панель
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topInset)
        ) {
            TopBar(onAboutClick, onProfileClick)
        }

        // Поле поиска
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 174.dp, start = 16.dp, end = 16.dp) // Отступ сверху - 54 пикселя
        ) {
//            Spacer(modifier = Modifier.height(54.dp))
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = { onFilterClick() },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(46.dp),
//                    .padding(horizontal = 30.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen, // Цвет фона кнопки
                    contentColor = LightBeige  // Цвет текста и иконки
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon), // Добавь свою иконку фильтра
                    contentDescription = "Фильтры",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Отступ между иконкой и текстом
                Text(text = "Фильтры", style = ExtraBoldLightBeige, fontSize = 17.sp)
            }


            Spacer(modifier = Modifier.height(26.dp))

            // Сетка карточек
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    top = 0.dp,
                    start = 0.dp,
                    end = 0.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(3) {
                    BirdCard(
                        name = "Малиновка",
                        imageRes = R.drawable.robin1,
                        repostCount = 10,
                        date = "14.02.2025",
                        onClick = onRobinCardClick
                    )
                }
            }
        }
    }
}