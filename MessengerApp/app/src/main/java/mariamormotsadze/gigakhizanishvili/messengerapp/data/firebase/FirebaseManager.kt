package mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase

import com.google.firebase.auth.FirebaseUser
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.UserModel

object FirebaseManager {
    fun firebaseUserToUser(firebaseUser: FirebaseUser): UserModel {
        return UserModel()
    }
}