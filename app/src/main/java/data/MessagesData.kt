package data

sealed class BaseMessageType {
    abstract val date: String
    abstract val time: String
}

data class MessageType(
    val text: String,
    val isFromMe: Boolean,
    override val date: String,
    override val time: String
) : BaseMessageType()

data class SharedMessageType(
    val postTitle: String,
    val postImage: ImageSource,
    val repostsCount: Int,
    val isFromMeShared: Boolean,
    override val date: String,
    override val time: String
) : BaseMessageType()
