package mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySearchUsersBinding

class UsersSearchActivity : AppCompatActivity() {

    private lateinit var activitySearchUsersBinding: ActivitySearchUsersBinding
    private lateinit var user: UserModel
    private lateinit var oldChats: HashMap<String, ChatServiceModel>
    private lateinit var adapter: FoundUsersAdapter

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
                    null, // image is not needed
                    serviceModel.imageName,
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

    private fun setUpSearchBoxListener() {
        Log.i("GAITANA", "AQ XO MAINC GAITANA")
        activitySearchUsersBinding.searchBox.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, end: Int) {
                Log.i("GAITANA", "aradasdasd")
                search(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun setup() {
        setupBinding()
        setUpSearchBoxListener()
        setupUsersRecyclerView()
        setupBackButton()
        setUpAdapter()
    }

    private fun search(filterStr: String){
        FirebaseDatabase.getInstance().reference
            .child("/users")
            .orderByChild("nickname")
            .startAt(filterStr)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val messages = snapshot.getValue(object: GenericTypeIndicator<HashMap<String, HashMap<String, Any>>>(){})
                    if (messages != null) {
                        val list: MutableList<FoundUserModel> = arrayListOf()
                        for (i in messages) {
                            val key = i.key
                            val nickname = i.value["nickname"] as String?
                            val profession = i.value["profession"] as String?
                            val image = i.value["image"] as String?
                            list.add(FoundUserModel(key, nickname, profession, image))
                        }
                        adapter.configure(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun setUpAdapter() {
        Log.d("ADAPTER", "ki")
//        adapter = FoundUsersAdapter()
        adapter = FoundUsersAdapter(listOf())
        activitySearchUsersBinding.foundUsersRecyclerView.adapter = adapter
//        search("gi")
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