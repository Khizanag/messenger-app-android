package mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object SignUpUseCase {
    fun signUp(nickname: String, password: String, profession: String): Boolean {
//        val auth = Firebase.auth
//        val email = "$nickname@gmail.com"
//        auth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "createUserWithEmail:success")
//                val user = auth.currentUser
////                updateUI(user)
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                updateUI(null)
//            }
//        }
        return true
    }
}