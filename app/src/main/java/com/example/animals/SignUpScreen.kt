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
fun SignUpScreen(onSignUpSuccess: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Регистрация",
            style = ButtonsExtraBold,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            color = DarkGreen,
            modifier = Modifier.padding(bottom = 99.dp)
        )

        SignUpInputField(label = "Email:", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))

        SignUpPasswordField(
            label = "Пароль:",
            password = password,
            onPasswordChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SignUpPasswordField(
            label = "Подтверждение пароля:",
            password = confirmPassword,
            onPasswordChange = { confirmPassword = it },
            isPasswordVisible = isConfirmPasswordVisible,
            onToggleVisibility = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onSignUpSuccess() }, // Переход на CatalogScreen
            modifier = Modifier
                .padding(17.dp)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text(
                text = "Зарегистрироваться",
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
                color = DarkGreen,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}


