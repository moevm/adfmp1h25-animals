package Authentication.Registration

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
fun RegistrationInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp)) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = DarkGreen,
            fontFamily = Manrope,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = InputMediumGreen,
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
                            style = InputMediumGreen,
                            color = DarkGreen.copy(alpha = 0.65f)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun RegistrationPasswordField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp)) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = DarkGreen,
            fontFamily = Manrope,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBeige, shape = RoundedCornerShape(22.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = password,
                onValueChange = onPasswordChange,
                textStyle = InputMediumGreen,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (password.isEmpty()) {
                                Text(
                                    text = "qwerty",
                                    style = InputMediumGreen.copy(
                                        color = DarkGreen.copy(alpha = 0.65f)
                                    )
                                )
                            }
                            innerTextField()
                        }
                        IconButton(onClick = onToggleVisibility) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Включить видимость пароля"
                            )
                        }
                    }
                }
            )
        }
    }
}
