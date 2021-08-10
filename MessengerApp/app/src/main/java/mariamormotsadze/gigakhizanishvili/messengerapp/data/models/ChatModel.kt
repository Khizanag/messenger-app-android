package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

data class ChatModel (
    val id: String? = null,
    val firstUserId: String? = null,
    val secondUserId: String? = null,
    var messages: MutableList<MessageModel>,
)