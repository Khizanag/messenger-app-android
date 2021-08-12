package mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys

class ChatActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var model: ChatModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter

    private lateinit var username: TextView
    private lateinit var profession: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var messageTexField: EditText
    private lateinit var sendButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
        setupVariables()
        setListeners()
    }

    private fun init(){
        username = findViewById(R.id.chat_username)
        profession = findViewById(R.id.chat_user_profession)
        toolbar = findViewById(R.id.chat_toolbar)
        messageTexField = findViewById(R.id.message_text_field)
        sendButton = findViewById(R.id.send_button)

    }

    private fun setListeners(){
        setToolbarListener()
        setSendButtonListener()

    }

    private fun setToolbarListener() {
        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    private fun setSendButtonListener() {
        sendButton.setOnClickListener{
            sendMessage(messageTexField.text.toString())
        }
    }

    private fun sendMessage(toSend: String){
        Log.i("CHAT - message to send: ", toSend)
    }

    private fun setupVariables() {
        setupUserId()
        setupChatModel()
        setUpHeader()
    }

    private fun setupUserId() {
        userId = Firebase.auth.currentUser!!.uid
    }

    private fun setupChatModel() {
        model = intent.extras!!.getSerializable(ExtraKeys.CHAT_TO_DISPLAY) as ChatModel
        Log.i("`", "CHAT: $model")
        recyclerView = findViewById(R.id.recycler_view)
        Log.i("CHAT", model.messages.toString())
//        adapter = ChatAdapter(model.messages.subList(1, model.messages.size))
        adapter = ChatAdapter(model.messages)
        recyclerView.adapter = adapter
    }

    private fun setUpHeader(){
        username.text = model.otherUser.nickname
        profession.text = model.otherUser.profession
    }

}