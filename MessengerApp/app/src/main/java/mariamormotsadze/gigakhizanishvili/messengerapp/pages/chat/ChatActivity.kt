package mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys

class ChatActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var model: ChatModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setupVariables()
    }

    private fun setupVariables() {
        setupUserId()
        setupChatModel()
    }

    private fun setupUserId() {
        userId = Firebase.auth.currentUser!!.uid
    }

    private fun setupChatModel() {
        model = intent.extras!!.getSerializable(ExtraKeys.CHAT_TO_DISPLAY) as ChatModel
        Log.i("`", "CHAT: $model")
    }

}