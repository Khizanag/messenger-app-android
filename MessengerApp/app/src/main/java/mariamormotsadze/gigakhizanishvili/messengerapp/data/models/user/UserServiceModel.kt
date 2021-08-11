package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserServiceModel (
    var nickname: String? = null,
    var imageUrl: String? = null,
    var profession: String? = null,
)