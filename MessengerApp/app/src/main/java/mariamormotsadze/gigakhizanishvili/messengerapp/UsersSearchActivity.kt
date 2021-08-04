package mariamormotsadze.gigakhizanishvili.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySearchUsersBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.fragments.HomeFragment

class UsersSearchActivity : AppCompatActivity() {

    private lateinit var activitySearchUsersBinding: ActivitySearchUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_users)
        activitySearchUsersBinding = ActivitySearchUsersBinding.inflate(layoutInflater)
        setContentView(activitySearchUsersBinding.root)

        val backButton = activitySearchUsersBinding.back

        backButton.setOnClickListener{
            finish()
        }
    }
}