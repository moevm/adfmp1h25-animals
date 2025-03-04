package Authentication.Registration

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegistrationScreen(onSignUpSuccess: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

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

        RegistrationInputField(label = "Email:", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))

        RegistrationPasswordField(
            label = "Пароль:",
            password = password,
            onPasswordChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegistrationPasswordField(
            label = "Подтверждение пароля:",
            password = confirmPassword,
            onPasswordChange = { confirmPassword = it },
            isPasswordVisible = isConfirmPasswordVisible,
            onToggleVisibility = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
        )

        Spacer(modifier = Modifier.height(20.dp))
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    errorMessage = "Все поля должны быть заполнены"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (password != confirmPassword) {
                    errorMessage = "Пароли не совпадают"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSignUpSuccess()
                        } else {
                            errorMessage = task.exception?.localizedMessage ?: "Ошибка регистрации"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            },
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
