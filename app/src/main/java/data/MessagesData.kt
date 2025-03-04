package data

open class MessageType(
    val text: String,
    val isFromMe: Boolean,
    val timestamp: String
)

data class SharedMessageType(
    val postTitle: String,
    val postImage: ImageSource,
    val sharedTimestamp: String = "",
    val repostsCount: Int,
    val isFromMeShared: Boolean,
) : MessageType(text = postTitle, isFromMe = isFromMeShared, timestamp = sharedTimestamp)
