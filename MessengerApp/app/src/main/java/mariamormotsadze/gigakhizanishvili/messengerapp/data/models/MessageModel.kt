package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

import java.util.*

data class MessageModel (
    val id: String? = null,
    val senderId: String? = null,
    val receiverId: String? = null,
    val sendingTime: Date? = null,
    val text: String? = null,
)