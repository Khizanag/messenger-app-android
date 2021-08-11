package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserFactory
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityHomePageBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home.HomeFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page.ProfilePageFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page.ProfilePageFragmentControllerInterface
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users.UsersSearchActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in.SignInActivity

class HomePageActivity : AppCompatActivity(), ProfilePageFragmentControllerInterface {

    private lateinit var activityHomeBinding: ActivityHomePageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runGetUserTask()
    }

    private fun runGetUserTask() {
        val userRef = FirebaseManager.getSignedInUserReference()
        val getUserTask = userRef.get()
        getUserTask.addOnSuccessListener { userDataSnapshot ->
            val serviceModel = userDataSnapshot.getValue<UserServiceModel>()
            val userId = userDataSnapshot.key

            if (userId == null || serviceModel == null) {
                Log.i("`firebase`", "error during service model")
                showMessage(R.string.connection_error)
            } else {
                Log.i("`firebase`", "NO error during getSignedInUserId ")
                user = UserFactory.toModel(userId, serviceModel!!)
                setup()
            }
        }
        getUserTask.addOnFailureListener { showMessage(R.string.connection_error) }
    }

    private fun setup() {
        setupBinding()
        setupNavigationView()
        setupBottomTabBar()
    }

    private fun setupBinding() {
        activityHomeBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
    }

    private fun setupSignedInUser() {
//        user = FirebaseManager.getSignedInUser()
    }

    private fun setupNavigationView(){
        bottomNavigationView = activityHomeBinding.bottomNavigationView
        bottomNavigationView.background = null
    }

    private fun setupBottomTabBar() {
        setupFab()

        val homeFragment = HomeFragment()
        val settingsFragment = ProfilePageFragment(this, user)
        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_icon -> makeCurrentFragment(homeFragment)
                R.id.settings_icon -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setupFab() {
        val fab = activityHomeBinding.searchUsersFloatingButton
        fab.setOnClickListener{
            Log.i("Home Fragment", "FAB CLICKED")
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

