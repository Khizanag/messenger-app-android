package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel

data class UserModel(
    val id: String,
    var nickname: String,
    var imageUrl: String? = null,
    var profession: String,
    var chats: HashMap<String, ChatModel>? = null, // TODO try just Map
)