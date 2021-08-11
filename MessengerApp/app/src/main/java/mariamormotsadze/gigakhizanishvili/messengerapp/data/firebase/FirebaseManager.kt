package mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.data.fake_data.FakeUserData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.UserModel

object FirebaseManager {
    fun firebaseUserToUser(firebaseUser: FirebaseUser?): UserModel {
        if (firebaseUser == null) {
            return UserModel()
        } else {
            return UserModel()
        }
    }

    fun getSignedInUser(): UserModel {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.e("`firebase`", "Error during signing in in FirebaseManager")
        }
        return firebaseUserToUser(currentUser)
    }
}