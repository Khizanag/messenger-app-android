package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

data class UserModel(
    val id: String,
    var nickname: String,
    var imageUrl: String? = null,
    var profession: String,
)