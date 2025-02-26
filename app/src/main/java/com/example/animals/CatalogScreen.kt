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

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    var hasFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val isSearchActive = hasFocus || query.isNotEmpty()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .background(LightBeige, shape = RoundedCornerShape(25.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(20.dp)
                )

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = InputMediumGreen.copy(fontSize = 18.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp, top = 10.dp)
                        .onFocusChanged { focusState ->
                            hasFocus = focusState.isFocused
                        }
                        .focusRequester(focusRequester),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box {


                            if (query.isEmpty() && !hasFocus) {
                                Text(
                                    text = "Поиск",
                                    style = InputMediumGreen,
                                    color = LightGreen,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        if (isSearchActive) {
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = {
                    onQueryChange("")
//                    isSearchActive = false
                    focusManager.clearFocus()
                    focusRequester.freeFocus()
                },
                shape = RoundedCornerShape(25.dp), // 1. Установите форму здесь
                modifier = Modifier
                    .background(Brown, RoundedCornerShape(25.dp)) // 2. Добавьте форму в background
                    .clip(RoundedCornerShape(25.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Brown, // 3. Используйте правильный цвет фона
                    contentColor = LightBeige
                )
            ) {
                Text(
                    text = "Отмена",
                    style = NormalLightBeige,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun BirdCard(
    name: String,
    imageRes: Int,
    repostCount: Int,
    date: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Bird",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillHeight
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightBeige)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = name,
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
                                contentDescription = "Repost",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = repostCount.toString(),
                                style = InputMediumGreen,
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            text = date,
                            style = InputMediumGreen,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(onAboutClick: () -> Unit, onProfileClick: () -> Unit) {
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
                Log.d("CatalogScreen", "Info button clicked")
                onAboutClick()
            },
            modifier = Modifier.size(60.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.info),
                contentDescription = "Информация",
                modifier = Modifier.size(40.dp)
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
                Log.d("CatalogScreen", "Profile button clicked")
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
