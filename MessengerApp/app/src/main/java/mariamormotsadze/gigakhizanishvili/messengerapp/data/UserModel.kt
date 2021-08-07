package mariamormotsadze.gigakhizanishvili.messengerapp.data

import android.graphics.drawable.Drawable
import java.io.Serializable

data class UserModel (
    val id: Int,
    var nickname: String,
    val password: String,
    val imageUrl: String?,
    var profession: String,
): Serializable