package com.example.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*

@Composable
fun LogInScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход",
            style = ButtonsExtraBold,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            color = Brown,
            modifier = Modifier.padding(bottom = 99.dp)
        )

        LoginInputField(label = "Email:", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))

        LoginPasswordField(
            label = "Пароль:",
            password = password,
            onPasswordChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onLoginSuccess() },
            modifier = Modifier
                .padding(17.dp)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Brown)
        ) {
            Text(
                text = "Войти",
                style = ButtonsExtraBold,
                fontWeight = FontWeight.ExtraBold,
                color = LightBeige,
                modifier = Modifier.padding(6.dp)
            )
        }

        TextButton(onClick = onBack) {
            Text(
                text = "Назад",
                style = ButtonsExtraBold,
                fontWeight = FontWeight.ExtraBold,
                color = Brown,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@Composable
fun LoginInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp)) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Brown,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = InputMediumBrown,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightBeige, shape = RoundedCornerShape(22.dp))
                        .padding(13.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "user@gmail.com",
                            style = InputMediumBrown,
                            color = Brown.copy(alpha = 0.65f) // или любой другой цвет для плейсхолдера
                        )
                    }
                    innerTextField()

                }
            }
        )
    }
}
