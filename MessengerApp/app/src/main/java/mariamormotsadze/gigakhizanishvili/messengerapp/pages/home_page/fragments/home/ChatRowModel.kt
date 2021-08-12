package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import android.media.Image
import com.google.firebase.database.IgnoreExtraProperties
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import java.util.*

// this class supports each recycler view row on
// home page's home fragment
data class ChatRowModel(
    val otherUser: UserModel,
    val lastMessage: String? = null,
    private val lastMessageSendingTime: String,
) {
    val timeFromLastMessage: String = "$lastMessageSendingTime"
    // TODO change for correct format
}