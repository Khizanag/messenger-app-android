package mariamormotsadze.gigakhizanishvili.messengerapp.data

import java.util.*


data class ChatModel (
    val id: Int,
    val firstUserId: Int,
    val secondUserId: Int,
    val lastModifiedDate: Date,
)