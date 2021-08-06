package mariamormotsadze.gigakhizanishvili.messengerapp.data

import android.media.Image

data class UserModel(
    val id: Int,
    val fullName: String,
    val email: String,
    val password: String,
    val photo: Image,
)