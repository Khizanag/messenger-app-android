package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat

import com.google.firebase.database.IgnoreExtraProperties
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel
import java.io.Serializable

@IgnoreExtraProperties
data class ChatServiceModel(
    var messages: List<MessageModel>? = null,
): Serializable