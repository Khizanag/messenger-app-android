package mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.FakeData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.HomePageActivity
import java.io.Serializable

object SignInUseCase {
    fun signIn(nickname: String, password: String): UserModel? {
        return FakeData.getUser1()
    }
}