package mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.data.fake_data.FakeUserData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserFactory
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants

object FirebaseManager {
    fun firebaseUserToUser(firebaseUser: FirebaseUser?): UserModel {
//        val userId = firebaseUser!!.uid
//        val database = Firebase.database
//        val usersRef = database.getReference(DatabaseConstants.USERS)
//        val serviceUser = usersRef.child(userId).get().addOnCompleteListener {
//            !!.getValue<UserServiceModel>()
//        }
//        // TODO handle serviceUser when null
//        return UserFactory.toModel(userId, serviceUser!!)
        return FakeUserData.getUser1()
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