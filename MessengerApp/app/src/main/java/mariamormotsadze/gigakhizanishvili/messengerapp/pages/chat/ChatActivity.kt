package mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants.ONE_MEGABYTE
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys

class ChatActivity : AppCompatActivity() {

    private lateinit var messages: List<MessageModel>
    private lateinit var otherUser: UserModel
    private val storageRef = FirebaseStorage.getInstance().reference

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
        setupChatModel()
        setUpHeader()
    }

    private fun setupChatModel() {
        recyclerView = findViewById(R.id.recycler_view)

        intent.extras?.let { extras ->

            val chatId = extras.getString(ExtraKeys.WHOSE_CHAT_USER_ID)!!

            val chatRef = Firebase.database.getReference(
                "${DatabaseConstants.USERS}/${Firebase.auth.currentUser!!.uid}/${DatabaseConstants.CHATS}/${chatId}")
            chatRef.get().addOnSuccessListener { data ->
                val chatServiceModel = data.getValue<ChatServiceModel>()
                messages = chatServiceModel!!.messages!!
            }.addOnFailureListener { messages = listOf() }

            val database = Firebase.database
            val usersRef = database.getReference(DatabaseConstants.USERS)
            val otherUserId = chatId
            val otherServiceUserRef = usersRef.child(otherUserId)

            val getOtherServiceUserTask = otherServiceUserRef.get()
            getOtherServiceUserTask.addOnSuccessListener { otherUserDataSnapshot ->
                val otherServiceModel = otherUserDataSnapshot.getValue<UserServiceModel>()!!
                otherUser = UserModel(otherUserId, otherServiceModel.nickname!!, null, otherServiceModel.imageName, otherServiceModel.profession!!, null)


                val imageRef = storageRef.child("images/${otherUser.imageName}")
                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { byteArray ->
                    Log.i("```", "imageRef was downed successfully")
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
                    otherUser.image = bitmap

                    Log.i("`", "CHAT: $messages; otherUser: $otherUser")
                    adapter = ChatAdapter(messages)
                    recyclerView.adapter = adapter
                }
            }
        }
    }


    private fun setUpHeader(){
        username.text = otherUser.nickname
        profession.text = otherUser.profession
    }

}