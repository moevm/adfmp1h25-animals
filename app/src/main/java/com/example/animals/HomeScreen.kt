package com.example.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.Brown
import com.example.animals.ui.theme.ButtonsExtraBold
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.LightBeige

@Composable
fun HomeScreen(onLoginClick: () -> Unit, onSignUpClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige),  // Устанавливаем фон
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .padding(17.dp, 15.dp)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text(
                text = "Регистрация",
                style = ButtonsExtraBold, // Примените нужный стиль для заголовка
                fontWeight = FontWeight.ExtraBold,
                color = LightBeige,
                modifier = Modifier.padding(6.dp)
            )
        }

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .padding(17.dp, 15.dp)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Brown)
        ) {
            Text(
                text = "Вход",
                style = ButtonsExtraBold, // Примените нужный стиль для заголовка
                fontWeight = FontWeight.ExtraBold,
                color = LightBeige,
                modifier = Modifier.padding(6.dp)
            )
        }


    }
}
