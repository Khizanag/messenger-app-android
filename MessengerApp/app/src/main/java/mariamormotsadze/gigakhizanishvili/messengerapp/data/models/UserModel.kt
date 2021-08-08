package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

import java.io.Serializable

data class UserModel(
    val id: Int,
    var nickname: String,
    val password: String,
    var imageUrl: String,
    var profession: String,
): Serializable