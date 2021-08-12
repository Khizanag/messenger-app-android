package mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ktx.getValue
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserFactory
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySearchUsersBinding

class UsersSearchActivity : AppCompatActivity() {

    private lateinit var activitySearchUsersBinding: ActivitySearchUsersBinding
    private lateinit var user: UserModel
    private lateinit var oldChats: HashMap<String, ChatServiceModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userRef = FirebaseManager.getSignedInUserReference()
        val getUserTask = userRef.get()
        getUserTask.addOnSuccessListener { userDataSnapshot ->
            val serviceModel = userDataSnapshot.getValue<UserServiceModel>()
            serviceModel?.let {
                user = UserModel(
                    userDataSnapshot.key!!,
                    serviceModel.nickname!!,
                    serviceModel.imageUrl,
                    serviceModel.profession!!,
                    hashMapOf(), // chats are not needed
                )
                oldChats = serviceModel.chats!!

            }
            // TODO
            setup()
        }
        getUserTask.addOnFailureListener { showMessage(R.string.connection_error) }
    }

    private fun setup() {
        setupBinding()
        setupUsersRecyclerView()
        setupBackButton()
    }

    private fun setupUsersRecyclerView() {
        val foundUsers = listOf<FoundUserModel>()
        activitySearchUsersBinding.foundUsersRecyclerView.adapter = FoundUsersAdapter(foundUsers)
    }

    private fun setupBinding() {
        activitySearchUsersBinding = ActivitySearchUsersBinding.inflate(layoutInflater)
        setContentView(activitySearchUsersBinding.root)
    }

    private fun setupBackButton() {
        val backButton = activitySearchUsersBinding.back
        backButton.setOnClickListener{ finish() }
    }

    private fun showMessage(messageResourceId: Int) {
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show()
    }
}