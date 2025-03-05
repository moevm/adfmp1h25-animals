package ScreenTransitions


//import HomeScreen
import AboutAppInfo.AboutScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import AnimalCard.AnimalCardScreen
import AnimalsFilter.FilterScreen
import Authentication.Login.LogInScreen
import Authentication.Registration.RegistrationScreen
import Catalog.CatalogScreen
import Chat.ChatScreen
import Home.HomeScreen
import Profile.ProfileNavigation.ProfileScreen
import Welcome.WelcomeScreen
import android.util.Log
import data.AnimalType


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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

val initialTypeFilters = mapOf(
    "Млекопитающие" to true,
    "Птицы" to true,
    "Рептилии" to true,
    "Земноводные" to true
)

val initialLocationFilters = mapOf(
    "Парк" to true,
    "Лес/Роща" to true,
    "Водоем" to true,
    "Двор/Крыша" to true
)

val initialSizeFilters = mapOf(
    "Маленький" to true,
    "Средний" to true,
    "Большой" to true
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator() {
    var animalFilters by remember { mutableStateOf(initialTypeFilters) }
    var sizeFilters by remember { mutableStateOf(initialSizeFilters) }
    var locationFilters by remember { mutableStateOf(initialLocationFilters) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }
    var selectedAnimal by remember { mutableStateOf<AnimalType?>(null) }
    var selectedName by remember { mutableStateOf<String?>(null) }

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

        Screen.SignUp -> RegistrationScreen(
            onSignUpSuccess = { currentScreen = Screen.Catalog },
            onBack = { currentScreen = Screen.Home }
        )

        Screen.Catalog -> {
            selectedAnimal = null
            CatalogScreen(
                filters = Triple(animalFilters, sizeFilters, locationFilters),
                onAboutClick = { currentScreen = Screen.About },
                onProfileClick = { currentScreen = Screen.Profile },
                onAnimalCardClick = { animal ->
                    selectedAnimal = animal
                    currentScreen = Screen.RobinCard
                },
                onFilterClick = { currentScreen = Screen.Filter }
            )
        }

        Screen.About -> AboutScreen(
            onBackClick = { currentScreen = Screen.Catalog },
            onProfileClick = { currentScreen = Screen.Profile }
        )

        Screen.Profile -> {
            selectedAnimal = null
            selectedName = null
            ProfileScreen(
                onBack = { currentScreen = Screen.Catalog },
                onPostClick = { animal ->
                    selectedAnimal = animal
                    currentScreen = Screen.RobinCard
                },
                onChatClick = { userName ->
                    selectedName=userName
                    currentScreen = Screen.Chat },
                onLogout = { currentScreen = Screen.Home }
            )
        }

        Screen.RobinCard -> selectedAnimal?.let { animal ->
            AnimalCardScreen(
                animal = animal,
                onBackClick = { currentScreen = Screen.Catalog },
                onProfileClick = { currentScreen = Screen.Profile }
            )
        }

        Screen.Filter -> FilterScreen(
            currentAnimalFilters = animalFilters,
            currentSizeFilters = sizeFilters,
            currentLocationFilters = locationFilters,
            onCancel = {
//                Log.d("AppNavigator", "Фильтры отменены")
                currentScreen = Screen.Catalog
            },
            onApply = { newAnimalFilters, newSizeFilters, newLocationFilters ->
//                Log.d("AppNavigator", "Фильтры применены: $newAnimalFilters, $newSizeFilters, $newLocationFilters")
                animalFilters = newAnimalFilters
                sizeFilters = newSizeFilters
                locationFilters = newLocationFilters
                currentScreen = Screen.Catalog
            }
        )

        Screen.Chat -> selectedName?.let {
            selectedAnimal = null
            ChatScreen(
                name= it,
                onBack = { currentScreen = Screen.Profile },
                onSharedMessageClick = {animal ->
                    Log.d("Screens", "Найденное животное: ${animal}")
                    selectedAnimal = animal
                    currentScreen = Screen.RobinCard
                }
            )
        }
    }
}


