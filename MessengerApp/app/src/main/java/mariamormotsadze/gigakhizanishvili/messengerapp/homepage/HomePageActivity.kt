package mariamormotsadze.gigakhizanishvili.messengerapp.homepage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.UsersSearchActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityHomePageBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.HomeFragment
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.SettingsFragment

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
       // removeTextLabel(bottomNavigationView, (R.id.home_icon))
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

    private fun removeTextLabel(bottomNavigationView: BottomNavigationView, @IdRes menuItemId: Int) {
        val view = bottomNavigationView.findViewById<View>(menuItemId) ?: return
        if (view is ItemView) {
            val viewGroup = view as ViewGroup
            var padding = 0
            for (i in 0 until viewGroup.childCount) {
                val v = viewGroup.getChildAt(i)
                if (v is ViewGroup) {
                    padding = v.getHeight()
                    viewGroup.removeViewAt(i)
                }
            }
            viewGroup.setPadding(
                view.getPaddingLeft(),
                (viewGroup.paddingTop + padding) / 2,
                view.getPaddingRight(),
                view.getPaddingBottom()
            )
        }
    }
}

