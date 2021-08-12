package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class MessageModel (
    val id: String? = null,
    val sendTime: String? = null,
    val text: String? = null,
    val amISender: Boolean = false,
)