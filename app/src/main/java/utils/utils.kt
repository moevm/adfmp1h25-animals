package utils

import android.os.Build
import androidx.annotation.RequiresApi
import data.MessageType
import data.SharedMessageType
import data.UsersData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return LocalDate.now().format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return LocalDateTime.now().format(formatter)
}

fun getLastMessageByUserName(userName: String): String? {
    val user = UsersData.users.find { it.name == userName }
    return user?.messages?.lastOrNull()?.let {
        when (it) {
            is MessageType -> it.text
            is SharedMessageType -> "Поделился постом: ${it.postTitle}"
        }
    }
}

fun getLastMessageDateTimeByUserName(userName: String): String? {
    val user = UsersData.users.find { it.name == userName }
    return user?.messages?.lastOrNull()?.let { "${it.date} ${it.time}" }
}
