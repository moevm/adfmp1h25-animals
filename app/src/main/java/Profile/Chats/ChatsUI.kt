package Profile.Chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.ExtraBoldGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import data.UsersData
import data.animalList
import utils.getLastMessageByUserName
import utils.getLastMessageDateTimeByUserName


@Composable
fun MessagesSection(searchQuery: String, onItemClick: (name: String) -> Unit) {
    val filteredMessages = remember(searchQuery) {
        UsersData.users.filter { user -> user.name.contains(searchQuery, ignoreCase = true)}
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBeige)
    ) {
        filteredMessages.forEach { user ->
            MessageItem(
                avatarRes = user.avatar,
                userName = user.name,
                onClick = { onItemClick(user.name) }
            )
            Divider(color = DarkBeige, thickness = 1.dp)
        }
    }
}

@Composable
fun MessageItem(avatarRes: Int, userName: String, onClick: () -> Unit) {
    val lastMessage = getLastMessageByUserName(userName)
    val timestamp = getLastMessageDateTimeByUserName(userName)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = "Аватар",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = userName,
                style = ExtraBoldGreen,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lastMessage ?: "Нет сообщений",
                style = InputMediumGreen,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = timestamp.toString(),
                style = InputMediumGreen,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
