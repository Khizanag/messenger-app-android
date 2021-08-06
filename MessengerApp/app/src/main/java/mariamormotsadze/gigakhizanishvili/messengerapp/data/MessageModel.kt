package mariamormotsadze.gigakhizanishvili.messengerapp.data

import java.util.*

data class MessageModel (
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val sendingTime: Date,
    val text: String,
)