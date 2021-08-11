package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatModel (
    val id: String? = null,
    val firstUserId: String? = null,
    val secondUserId: String? = null,
    var messages: MutableList<MessageModel>,
)