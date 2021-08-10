package mariamormotsadze.gigakhizanishvili.messengerapp.data.models

import java.io.Serializable

data class UserModel(
    val id: String? = null,
    var nickname: String? = null,
    val password: String? = null,
    var imageUrl: String? = null,
    var profession: String? = null,
): Serializable