package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityHomePageBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat.ChatActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home.HomeFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page.ProfilePageFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page.ProfilePageFragmentControllerInterface
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users.UsersSearchActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in.SignInActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants.ONE_MEGABYTE
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys

class HomePageActivity : AppCompatActivity(), ProfilePageFragmentControllerInterface {

    private lateinit var activityHomeBinding: ActivityHomePageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var user: UserModel

    private val storageRef = FirebaseStorage.getInstance().reference

    private lateinit var homeFragment: HomeFragment
    private lateinit var profilePageFragment: ProfilePageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {

        val userRef = FirebaseManager.getSignedInUserReference()
        val getUserTask = userRef.get()

        getUserTask.addOnSuccessListener { userDataSnapshot ->
            val serviceModel = userDataSnapshot.getValue<UserServiceModel>()
            val userId = userDataSnapshot.key

            if (userId == null || serviceModel == null) {
                showMessage(R.string.connection_error)
            } else {
                user = UserModel(userId, serviceModel.nickname!!, null, serviceModel.imageName, serviceModel.profession!!, hashMapOf(),)

                val imageRef = storageRef.child("images/${user.imageName}")

                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size);
                    user.image = bitmap

                    setupUserChatsAndUI(serviceModel)
                }.addOnFailureListener {}
            }
        }
        getUserTask.addOnFailureListener { showMessage(R.string.connection_error) }
    }

    private fun setupUserChatsAndUI(serviceModel: UserServiceModel) {
        var numChatsToLoad = serviceModel.chats!!.size * 2 // for other user and for other user's image

        fun checkIfServiceHasFinished() {
            var isFinished: Boolean
            synchronized(numChatsToLoad) {
                numChatsToLoad--
                isFinished = numChatsToLoad == 0
            }
            if(isFinished) {
                setupUI()
            }
        }

        serviceModel.chats?.let { serviceChats ->
            for((otherUserId, chat) in serviceChats) {
                val database = Firebase.database
                val usersRef = database.getReference(DatabaseConstants.USERS)
                val otherServiceUserRef = usersRef.child(otherUserId)

                val getOtherServiceUserTask = otherServiceUserRef.get()
                getOtherServiceUserTask.addOnSuccessListener { otherUserDataSnapshot ->
                    val otherServiceUser = otherUserDataSnapshot.getValue<UserServiceModel>()
                    otherServiceUser?.let {
                        Log.i("```", "otherServiceUser: $otherServiceUser")
                        val otherUser = UserModel(
                            otherUserId,
                            it.nickname!!,
                            null,
                            it.imageName,
                            it.profession!!,
                            null,
                        )

                        val imageRef = storageRef.child("images/${otherUser.imageName}")

                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { byteArray ->
                            Log.i("```", "imageRef was downed successfully")

                            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
                            otherUser.image = bitmap
                            checkIfServiceHasFinished()
                        }.addOnFailureListener { checkIfServiceHasFinished() }

                        user.chats?.set(otherUserId, ChatModel(otherUser, chat.messages!!))
                    }
                    checkIfServiceHasFinished()
                }.addOnFailureListener { checkIfServiceHasFinished() }
            }
        }
    }

    private fun setupUI() {
        setupBinding()
        setupNavigationView()
        setupBottomTabBar()
    }

    private fun setupBinding() {
        activityHomeBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
    }

    private fun setupNavigationView(){
        bottomNavigationView = activityHomeBinding.bottomNavigationView
        bottomNavigationView.background = null
    }

    private fun setupBottomTabBar() {
        setupFab()

        homeFragment = HomeFragment(user)
        homeFragment.onChatItemClick = { chatRowModel ->
            val intent = Intent(this, ChatActivity::class.java)
            val chatToDisplay = user.chats?.get(chatRowModel.otherUser.id)!!
            Log.i("```", "chatsToDisplay: $chatToDisplay")
            intent.putExtra(ExtraKeys.CHAT_TO_DISPLAY, chatToDisplay)
            Log.i("```", "chatsToDisplay: put in extra succs")
            startActivity(intent)
        }
        profilePageFragment = ProfilePageFragment(this, user)
        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_icon -> makeCurrentFragment(homeFragment)
                R.id.settings_icon -> makeCurrentFragment(profilePageFragment)
            }
            true
        }
    }

    private fun setupFab() {
        val fab = activityHomeBinding.searchUsersFloatingButton
        fab.setOnClickListener{
            val intent = Intent(this, UsersSearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }

    private fun showMessage(messageResourceId: Int) {
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show()
    }

    // ----------- SettingsFragmentControllerInterface ---------- //

    override fun updateUser(newModel: UserModel) {
        user = newModel
    }

    override fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}

