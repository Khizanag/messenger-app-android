package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class UserServiceModel (
    @PropertyName("nickname")   var nickname: String? = null,
    @PropertyName("imageUrl")   var imageUrl: String? = null,
    @PropertyName("profession") var profession: String? = null,
)