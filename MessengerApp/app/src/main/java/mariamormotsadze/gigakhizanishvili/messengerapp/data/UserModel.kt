package mariamormotsadze.gigakhizanishvili.messengerapp.data

import java.io.Serializable

data class UserModel(
    val id: Int,
    var nickname: String,
    val password: String,
    val imageUrl: String,
    var profession: String,
): Serializable