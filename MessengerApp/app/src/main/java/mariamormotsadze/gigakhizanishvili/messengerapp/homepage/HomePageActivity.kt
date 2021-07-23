package mariamormotsadze.gigakhizanishvili.messengerapp.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.HomeFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.SettingsFragment

class HomePageActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home_page)
//    }

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initNavigationView()

        val homeFragment = HomeFragment()
        val settingsFragment = SettingsFragment()

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
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.background = null
    }

    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}

