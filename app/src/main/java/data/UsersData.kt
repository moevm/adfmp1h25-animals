package data

import com.example.animals.R

data class UserType(
    val name: String,
    val avatar: Int,
    val messages: MutableList<BaseMessageType>
)

object UsersData {
    val users = listOf(
        UserType(
            name = "Максим",
            avatar = R.drawable.max_avatar,
            messages = mutableListOf(
                MessageType("Привет! Как твои дела?", false, "10.02.2025", "09:30"),
                MessageType("Привет! Все хорошо, спасибо. А у тебя?", true, "10.02.2025", "09:35"),
                SharedMessageType("Голубь", ImageSource.Drawable(R.drawable.pigeon), 5, true, "10.02.2025", "09:40"),
                MessageType("Как тебе фотографии?", true, "10.02.2025", "09:45"),
                MessageType("Очень круто, мне нравится!", false, "10.02.2025", "09:50"),
                MessageType("Спасибо, рад, что понравилось!", true, "10.02.2025", "09:55"),
            )
        ),
        UserType(
            name = "Лилия",
            avatar = R.drawable.lily_avatar,
            messages = mutableListOf(
                MessageType("Как дела? Как прошел день?", false, "11.02.2025", "10:15"),
                MessageType("Привет! Все хорошо, день был насыщенный. А твой?", true, "11.02.2025", "10:20"),
                SharedMessageType("Лиса", ImageSource.Drawable(R.drawable.fox), 7, true, "11.02.2025", "10:25"),
                SharedMessageType("Лиса", ImageSource.Drawable(R.drawable.fox), 7, false, "11.02.2025", "10:30"),
                MessageType("Как тебе моя лиса?", true, "11.02.2025", "10:35"),
                MessageType("Очень классная! Обожаю этих животных.", false, "11.02.2025", "10:40"),
                MessageType("Рада, что понравилась!", true, "11.02.2025", "10:45"),
            )
        ),
        UserType(
            name = "Федор",
            avatar = R.drawable.fedor_avatar,
            messages = mutableListOf(
                MessageType("Слушай, как тебе мои новые фотки?", false, "12.02.2025", "11:00"),
                MessageType("Привет! Очень крутые фото, здорово получилось!", true, "12.02.2025", "11:05"),
                MessageType("Рад, что оценил!", true, "12.02.2025", "11:10"),
                MessageType("Да, фотографии просто шикарные!", false, "12.02.2025", "11:15"),
                MessageType("Спасибо, это очень приятно слышать!", true, "12.02.2025", "11:20"),
            )
        )
    )
}