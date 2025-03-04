package Catalog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.animals.R
import com.example.animals.ui.theme.Brown
import com.example.animals.ui.theme.LightGreen
import com.example.animals.ui.theme.NormalLightBeige



@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    var hasFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isSearchActive = hasFocus || query.isNotEmpty()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .clickable { focusRequester.requestFocus()}
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
                            if (focusState.isFocused) {
                                keyboardController?.show() // Включаем клавиатуру
                            }
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
                    focusManager.clearFocus()
                    focusRequester.freeFocus()
                },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .background(Brown, RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(25.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Brown,
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
