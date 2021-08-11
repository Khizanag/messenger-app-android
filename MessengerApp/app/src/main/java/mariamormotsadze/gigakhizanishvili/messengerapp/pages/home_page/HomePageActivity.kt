package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
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
        setup(savedInstanceState)
    }

    public override fun onStart() {
        super.onStart()
        user = FirebaseManager.getSignedInUser()
        Log.i("`firebase`", "user -> $user")
    }

    private fun setup(savedInstanceState: Bundle?) {
        setupBinding()
        setupSignedInUser()
        setupNavigationView()
        setupBottomTabBar()
    }

    private fun setupBinding() {
        activityHomeBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
    }

    private fun setupSignedInUser() {
        user = FirebaseManager.getSignedInUser()
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

