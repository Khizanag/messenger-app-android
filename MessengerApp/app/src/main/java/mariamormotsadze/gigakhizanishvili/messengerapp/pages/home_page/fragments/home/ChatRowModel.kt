package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import android.media.Image
import java.util.*


// this class supports each recycler view row on
// home page's home fragment
data class ChatRowModel(
    val id: String? = null,
    val secondPersonId: String? = null,
    val nickname: String? = null,
    val lastMessage: String? = null,
    val lastMessageSendingTime: Date? = null,
    val profilePhoto: Image? = null,
)