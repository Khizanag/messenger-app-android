package mariamormotsadze.gigakhizanishvili.messengerapp.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityHomePageBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.HomeFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.SettingsFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.UsersSearchActivity

class HomePageActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home_page)
//    }

    private lateinit var activityHomeBiding: ActivityHomePageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        activityHomeBiding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(activityHomeBiding.root)
        initNavigationView()

        val homeFragment = HomeFragment()
        val settingsFragment = SettingsFragment()

        val fab = activityHomeBiding.searchUsersFloatingButton
        fab.setOnClickListener{
            Log.i("Home Fragment", "FAB CLICKED")
            val intent = Intent(this, UsersSearchActivity::class.java)
            startActivity(intent)
        }

        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_icon -> makeCurrentFragment(homeFragment)
                R.id.settings_icon -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun initNavigationView(){
        bottomNavigationView = activityHomeBiding.bottomNavigationView
        bottomNavigationView.background = null
    }

    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}

