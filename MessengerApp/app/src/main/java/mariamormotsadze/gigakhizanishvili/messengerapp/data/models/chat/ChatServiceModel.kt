package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat

import com.google.firebase.database.IgnoreExtraProperties
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel

@IgnoreExtraProperties
data class ChatServiceModel(
    var messages: HashMap<String, MessageModel>? = null,
)