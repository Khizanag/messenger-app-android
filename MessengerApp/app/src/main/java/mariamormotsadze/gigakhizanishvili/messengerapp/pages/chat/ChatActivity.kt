package mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.UserModel

class ChatActivity : AppCompatActivity() {

    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    override fun onStart() {
        super.onStart()
        user = FirebaseManager.getSignedInUser()
    }
}