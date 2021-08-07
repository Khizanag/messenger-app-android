package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityHomePageBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home.HomeFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.settings.SettingsFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.settings.SettingsFragmentControllerInterface
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users.UsersSearchActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in.SignInActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys

class HomePageActivity : AppCompatActivity(), SettingsFragmentControllerInterface {

    private lateinit var activityHomeBinding: ActivityHomePageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var model: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        setup(savedInstanceState)
    }

    private fun setup(savedInstanceState: Bundle?) {
        setupBinding()
        setupLoggedInUser()
        setupNavigationView()
        setupBottomTabBar()
    }

    private fun setupLoggedInUser() {
        val extras = this.intent.extras!!
        model = extras.getSerializable(ExtraKeys.LOGGED_IN_USER) as UserModel
    }

    private fun setupBinding() {
        activityHomeBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
//        activityHomeBinding.
    }

    private fun setupNavigationView(){
        bottomNavigationView = activityHomeBinding.bottomNavigationView
        bottomNavigationView.background = null
    }

    private fun setupBottomTabBar() {
        setupFab()

        val homeFragment = HomeFragment()
        val settingsFragment = SettingsFragment(this, model)
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
        model = newModel
    }

    override fun signOut() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.removeExtra(ExtraKeys.LOGGED_IN_USER)
        startActivity(intent)
    }
}

