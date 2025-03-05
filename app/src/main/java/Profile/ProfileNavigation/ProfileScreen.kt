package Profile.ProfileNavigation

import Profile.Chats.Chats
import Profile.NewPost.NewPost
import Profile.Posts.Posts
import Profile.Statistics.Statistics
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.animals.R
import data.AnimalType

enum class Section(val title: String) {
    Statistics("Статистика"),
    Posts("Публикации"),
    NewPost("Новая запись"),
    Chats("Сообщения");
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onPostClick: (AnimalType) -> Unit,
    onChatClick: (String) -> Unit,
    onLogout: () -> Unit
) {

    var activeSection by remember { mutableStateOf(Section.Chats.title) }
    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(top = topInset)) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = activeSection,
                                style = ExtraBoldBrown,
                                fontSize = 24.sp
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.left_back_brown),
                                contentDescription = "Назад",
                                tint = Color.Unspecified
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                painter = painterResource(id = R.drawable.exit),
                                contentDescription = "Выход",
                                tint = Color.Unspecified
                            )
                        }
                    },
                    backgroundColor = DarkBeige,
                    elevation = 0.dp
                )
            }
        },
        bottomBar = {

            BottomNavigationBar(
                sections = Section.entries.map { it.title },
                activeSection = activeSection,
                onSectionSelected = { activeSection = it }
            )
        },
        backgroundColor = DarkBeige
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (activeSection == Section.Statistics.title || activeSection == Section.Posts.title) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ProfileInfo()
                    if (activeSection == Section.Statistics.title) {
                        Statistics()
                    } else {
                        Posts(
                            authorName = "Алексей",
                            onAnimalCardClick = { animal ->
                                onPostClick(animal)
                            }
                        )
                    }
                }
            } else if (activeSection == Section.Chats.title) {
                Chats(onChatClick= onChatClick)
            }  else if (activeSection == Section.NewPost.title) {
                NewPost()
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Контент для раздела: $activeSection",
                        fontSize = 18.sp,
                        color = DarkGreen
                    )
                }
            }
        }
    }
}

