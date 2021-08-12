package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

import android.graphics.Bitmap
import android.media.Image
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel
import java.io.Serializable

data class UserModel(
    val id: String,
    var nickname: String,
    var image: Bitmap? = null,
    val imageName: String? = null,
    var profession: String,
    var chats: HashMap<String, ChatModel>? = null, // TODO try just Map
): Serializable