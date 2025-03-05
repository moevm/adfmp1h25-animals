package AnimalCard

import Chat.addSharedMessage
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.animals.R
import data.AnimalType
import data.UsersData



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalCardScreen(animal: AnimalType, onBackClick: () -> Unit, onProfileClick: () -> Unit) {
    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    var searchQuery by remember { mutableStateOf("") }

    // Состояние для управления видимостью модального окна
    var showBottomSheet by remember { mutableStateOf(false) }
    val view = LocalView.current

    var selectedImageIndex by remember { mutableStateOf(0) }
    var showFullScreenImage by remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            it.statusBarColor = LightGreen.toArgb()
            WindowInsetsControllerCompat(it, view).isAppearanceLightStatusBars = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topInset)
                .verticalScroll(rememberScrollState())
        ) {
            TopBarCard(onBackClick, onProfileClick)
            ImageSlider(
                imageList = animal.images,
                onImageClick = { index ->
                    selectedImageIndex = index
                    showFullScreenImage = true
                }
            )
            AnimalInfo(animal=animal, onShareClick = { showBottomSheet = true })
        }

        if (showFullScreenImage) {
            Dialog(
                onDismissRequest = { showFullScreenImage = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .clickable { showFullScreenImage = false },
                    contentAlignment = Alignment.Center
                ) {
                    FullScreenImageSlider(
                        images = animal.images,
                        initialPage = selectedImageIndex,
                        onDismiss = { showFullScreenImage = false }
                    )
                }
            }
        }

        // Модальное окно
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = rememberModalBottomSheetState(),
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent
            ) {
                Box (
                    modifier = Modifier.fillMaxWidth()
                        .background(LightGreen)
                )
                {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())

                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .background(Color(0xFF9AA57D))

                            ){
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(vertical = 5.dp, horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Поделиться",
                                        style = BoldGreen,
                                        fontSize = 24.sp
                                    )
                                    IconButton(onClick = { showBottomSheet = false }) {
                                        Image(
                                            painter = painterResource(id = R.drawable.close_share),
                                            contentDescription = "Закрыть",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                            Column (
                                modifier = Modifier.fillMaxWidth()
                                    .padding(16.dp),
                            ){
                                SearchBarUsers(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it }
                                )

                                Spacer(modifier = Modifier.height(26.dp))

                                UsersData.users.forEach { user ->
                                    ShareItem(
                                        userAvatar = user.avatar,
                                        userName = user.name,
                                        onSendClick = { addSharedMessage(user.messages, animal)
                                            showBottomSheet = false}
                                    )
                                }
                            }

                        }
                    }
                }


            }
        }
    }
}
