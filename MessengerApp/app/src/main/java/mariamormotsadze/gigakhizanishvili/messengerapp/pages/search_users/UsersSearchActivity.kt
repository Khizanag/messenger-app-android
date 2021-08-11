package mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.data.fake_data.FakeUserData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySearchUsersBinding

class UsersSearchActivity : AppCompatActivity() {

    private lateinit var activitySearchUsersBinding: ActivitySearchUsersBinding
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    public override fun onStart() {
        super.onStart()
        user = FirebaseManager.getSignedInUser()
    }

    private fun setup() {
        setupBinding()
        setupUsersRecyclerView()
        setupBackButton()
    }

    private fun setupUsersRecyclerView() {
        activitySearchUsersBinding.foundUsersRecyclerView.adapter = FoundUsersAdapter(FakeUserData.getFoundUsers(""))
    }

    private fun setupBinding() {
        activitySearchUsersBinding = ActivitySearchUsersBinding.inflate(layoutInflater)
        setContentView(activitySearchUsersBinding.root)
    }

    private fun setupBackButton() {
        val backButton = activitySearchUsersBinding.back
        backButton.setOnClickListener{ finish() }
    }
}