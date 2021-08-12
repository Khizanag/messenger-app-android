package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatServiceModel
import java.io.Serializable

@IgnoreExtraProperties
data class UserServiceModel (
    var nickname: String? = null,
    var imageName: String? = null,
    var profession: String? = null,
    var chats: HashMap<String, ChatServiceModel>? = null, // TODO try just Map
): Serializable