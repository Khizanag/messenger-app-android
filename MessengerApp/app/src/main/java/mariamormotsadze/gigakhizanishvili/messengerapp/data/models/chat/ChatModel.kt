package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat

import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import java.io.Serializable

data class ChatModel (
    val otherUser: UserModel,
    var messages: List<MessageModel>,
): Serializable