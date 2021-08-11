package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class MessageModel (
    val id: String? = null,
    val senderId: String? = null,
    val receiverId: String? = null,
    val sendingTime: Date? = null,
    val text: String? = null,
)