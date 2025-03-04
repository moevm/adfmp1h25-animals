package data

import androidx.compose.runtime.toMutableStateList
import com.example.animals.R

data class UserType(
    val name: String,
    val avatar: Int,
    val messages: MutableList<MessageType>
)

object UsersData {
    val users = listOf(
        UserType(
            name = "Максим",
            avatar = R.drawable.max_avatar,
            messages = mutableListOf(
                MessageType("Привет! Как твои дела?", false, "10.02.2025"),
                MessageType("Привет! Все хорошо, спасибо. А у тебя?", true, "10.02.2025"),
                SharedMessageType("Голубь", ImageSource.Drawable(R.drawable.pigeon), "10.02.2025", 5, true),
                MessageType("Как тебе фотографии?", true, "10.02.2025"),
                MessageType("Очень круто, мне нравится!", false, "10.02.2025"),
                MessageType("Спасибо, рад, что понравилось!", true, "10.02.2025"),
            )
        ),
        UserType(
            name = "Лилия",
            avatar = R.drawable.lily_avatar,
            messages = mutableListOf(
                MessageType("Как дела? Как прошел день?", false, "11.02.2025"),
                MessageType("Привет! Все хорошо, день был насыщенный. А твой?", true, "11.02.2025"),
                SharedMessageType("Лиса", ImageSource.Drawable(R.drawable.fox), "11.02.2025", 7, true),
                SharedMessageType("Лиса", ImageSource.Drawable(R.drawable.fox), "11.02.2025", 7, false),
                MessageType("Как тебе моя лиса?", true, "11.02.2025"),
                MessageType("Очень классная! Обожаю этих животных.", false, "11.02.2025"),
                MessageType("Рада, что понравилась!", true, "11.02.2025"),
            )
        ),
        UserType(
            name = "Федор",
            avatar = R.drawable.fedor_avatar,
            messages = mutableListOf(
                MessageType("Слушай, как тебе мои новые фотки?", false, "12.02.2025"),
                MessageType("Привет! Очень крутые фото, здорово получилось!", true, "12.02.2025"),
                MessageType("Рад, что оценил!", true, "12.02.2025"),
                MessageType("Да, фотографии просто шикарные!", false, "12.02.2025"),
                MessageType("Спасибо, это очень приятно слышать!", true, "12.02.2025"),
            )
        )
    ).map { it.copy(messages = it.messages.toMutableStateList()) }
}
