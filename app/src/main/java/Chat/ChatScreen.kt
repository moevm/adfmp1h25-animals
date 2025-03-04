package Chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.animals.ui.theme.DarkBeige
import androidx.compose.ui.platform.LocalDensity
import data.AnimalType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(name: String,
               onBack: () -> Unit = {},
               onSharedMessageClick: (animal: AnimalType) -> Unit ) {

    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) { insets.getTop(this).toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topInset)
        ) {
            TopBarChat(onBack = onBack, name=name)
            ChatMessagesList(name = name, onSharedMessageClick = {animal -> onSharedMessageClick(animal)})
        }
    }
}




