package Welcome
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

import androidx.compose.ui.unit.dp
import com.example.animals.R
import com.example.animals.ui.theme.DarkBeige


@Composable
fun WelcomeScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        try {
            delay(3000)
            onTimeout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_logo), // Укажите ваш логотип
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}

