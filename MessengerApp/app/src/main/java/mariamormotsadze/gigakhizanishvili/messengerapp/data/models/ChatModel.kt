package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

import android.os.Message
import java.util.*


data class ChatModel (
    val id: Int,
    val firstUserId: Int,
    val secondUserId: Int,
    var messages: MutableList<MessageModel>,
)