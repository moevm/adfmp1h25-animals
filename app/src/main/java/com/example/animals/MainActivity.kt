package com.example.animals


//import HomeScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.HomeScreen
import com.example.animals.LogInScreen
import com.example.animals.SignUpScreen
import com.example.animals.WelcomeScreen
import com.example.animals.AboutScreen
import com.example.animals.RobinCardScreen
import com.example.animals.FilterScreen
import com.example.animals.ChatScreen


import kotlinx.coroutines.delay


sealed class Screen {
    object Welcome : Screen()
    object Home : Screen()
    object Login : Screen()
    object SignUp : Screen()
    object Catalog : Screen()
    object About : Screen()
    object Profile : Screen()
    object RobinCard : Screen()
    object Filter : Screen()
    object Chat : Screen()
}


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun AppNavigator() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }

    when (currentScreen) {
        Screen.Welcome -> WelcomeScreen { currentScreen = Screen.Home }
        Screen.Home -> HomeScreen(
            onLoginClick = { currentScreen = Screen.Login },
            onSignUpClick = { currentScreen = Screen.SignUp }
        )
        Screen.Login -> LogInScreen(
            onLoginSuccess = { currentScreen = Screen.Catalog },
            onBack = { currentScreen = Screen.Home }
        )
        Screen.SignUp -> SignUpScreen(
            onSignUpSuccess = { currentScreen = Screen.Catalog },
            onBack = { currentScreen = Screen.Home }
        )
        Screen.Catalog -> CatalogScreen(
            onAboutClick = { currentScreen = Screen.About },
            onProfileClick = { currentScreen = Screen.Profile },
            onRobinCardClick = { currentScreen = Screen.RobinCard },
            onFilterClick = { currentScreen = Screen.Filter }
        )
        Screen.About -> AboutScreen(
            onBackClick = { currentScreen = Screen.Catalog },
            onProfileClick = { currentScreen = Screen.Profile }
        )
        Screen.Profile -> ProfileScreen(
            onBack = { currentScreen = Screen.Catalog },
            onRobinCardClick = { currentScreen = Screen.RobinCard },
            onChatClick = { currentScreen = Screen.Chat },
            onLogout = { currentScreen = Screen.Home }
        )
        Screen.RobinCard -> RobinCardScreen(
            onBackClick = { currentScreen = Screen.Catalog },
            onProfileClick = { currentScreen = Screen.Profile }
        )
        Screen.Filter -> FilterScreen(
            onCancel = { currentScreen = Screen.Catalog },
            onApply = { currentScreen = Screen.Catalog }
        )
        Screen.Chat -> ChatScreen(
            onBack = { currentScreen = Screen.Profile }
        )
    }
}


