package mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants.ONE_MEGABYTE
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys

class ChatActivity : AppCompatActivity() {

    private lateinit var messages: MutableList<MessageModel>
    private lateinit var otherUser: UserModel
    private val storageRef = FirebaseStorage.getInstance().reference

    private lateinit var chatRef: DatabaseReference
    private lateinit var myMessagesRef: DatabaseReference
    private lateinit var otherUserMessagesRef: DatabaseReference

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter

    private lateinit var username: TextView
    private lateinit var profession: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var messageTexField: EditText
    private lateinit var sendButton: ImageButton

    private var isSetUpDone = false

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
        toolbar.setNavigationOnClickListener{ finish() }
    }

    private fun setSendButtonListener() {
        sendButton.setOnClickListener{
            sendMessage(messageTexField.text.toString())
            messageTexField.setText("")
        }
    }

    private fun sendMessage(text: String){
        Log.i("CHAT - message to send: ", text)

        myMessagesRef.child("${messages.size}").setValue(MessageModel(null, "1 min", text, true))
        otherUserMessagesRef.child("${messages.size}").setValue(MessageModel(null, "1 min", text, false))
    }

    private fun setupVariables() {
        setupChatModel()
    }

    private fun setupChatModel() {
        recyclerView = findViewById(R.id.recycler_view)

        intent.extras?.let { extras ->

            val chatId = extras.getString(ExtraKeys.WHOSE_CHAT_USER_ID)!!

            val path = "${DatabaseConstants.USERS}/${Firebase.auth.currentUser!!.uid}/${DatabaseConstants.CHATS}/${chatId}"
            chatRef = Firebase.database.getReference(path)
            myMessagesRef = chatRef.child(DatabaseConstants.MESSAGES)
            val messagesListener = object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && isSetUpDone) {
                        val unMutableMessages = dataSnapshot.getValue<List<MessageModel>>()
                        messages = unMutableMessages as MutableList<MessageModel>
                        adapter.configure(messages)
                        recyclerView.scrollToPosition(messages.size-1)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }

            myMessagesRef.addValueEventListener(messagesListener)

            chatRef.get().addOnSuccessListener { data ->
                val chatServiceModel = data.getValue<ChatServiceModel>()
                messages = chatServiceModel!!.messages!! as MutableList<MessageModel>
            }.addOnFailureListener { messages = mutableListOf() }

            val database = Firebase.database
            val usersRef = database.getReference(DatabaseConstants.USERS)
            val otherUserId = chatId
            val otherServiceUserRef = usersRef.child(otherUserId)

            val getOtherServiceUserTask = otherServiceUserRef.get()
            getOtherServiceUserTask.addOnSuccessListener { otherUserDataSnapshot ->
                val otherServiceModel = otherUserDataSnapshot.getValue<UserServiceModel>()!!
                otherUser = UserModel(otherUserId, otherServiceModel.nickname!!, null, otherServiceModel.imageName, otherServiceModel.profession!!, null)
                otherUserMessagesRef = Firebase.database.getReference(
                                    "${DatabaseConstants.USERS}/${otherUser.id}/${DatabaseConstants.CHATS}/${DatabaseConstants.MESSAGES}")

                val imageRef = storageRef.child("${DatabaseConstants.IMAGES}/${otherUser.imageName}")
                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { byteArray ->
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
                    otherUser.image = bitmap

                    Log.i("`", "CHAT: $messages; otherUser: $otherUser")
                    setUpHeader()
                    adapter = ChatAdapter(messages)
                    recyclerView.adapter = adapter
                    isSetUpDone = true
                }
            }
        }
    }

    private fun setUpHeader(){
        username.text = otherUser.nickname
        profession.text = otherUser.profession
    }

}