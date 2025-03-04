package Authentication.Login

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
fun LogInScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
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
            text = "Вход",
            style = ButtonsExtraBold,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            color = Brown,
            modifier = Modifier.padding(bottom = 99.dp)
        )

        LoginInputField(label = "Почта:", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))

        LoginPasswordField(
            label = "Пароль:",
            password = password,
            onPasswordChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(20.dp))
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Введите почту и пароль"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onLoginSuccess()
                        } else {
                            errorMessage = task.exception?.localizedMessage ?: "Ошибка входа"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            },
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
