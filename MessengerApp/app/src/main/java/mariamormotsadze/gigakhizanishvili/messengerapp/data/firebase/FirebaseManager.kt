package mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants

object FirebaseManager {

    fun getSignedInUserId(): String? {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            return currentUser.uid
        }
        return null
    }

    fun getUserReference(userId: String): DatabaseReference {
        val database = Firebase.database
        val usersRef = database.getReference(DatabaseConstants.USERS)
        val serviceUser = usersRef.child(userId)
        return serviceUser
    }

    fun getSignedInUserReference()  = getUserReference(getSignedInUserId()!!)
}