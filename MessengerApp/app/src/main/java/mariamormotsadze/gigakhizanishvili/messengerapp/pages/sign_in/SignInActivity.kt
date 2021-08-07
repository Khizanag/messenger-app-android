
package mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityMainBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.HomePageActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.LoginUseCase
import java.io.Serializable

class SignInActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupBinding()
        setListeners()
    }

    private fun setupBinding() {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

    private fun setListeners(){
        setSignInButtonListener()
        setLoginPageSignUpButtonListener()
    }

    private fun setSignInButtonListener() {
        activityMainBinding.signInButton.setOnClickListener{
            Log.i("LoginPage", "sign in button clicked")
            val nickname = activityMainBinding.loginNicknameTextField.text.toString()
            val password = activityMainBinding.loginPasswordTextField.text.toString()
            Log.i("LoginPage", "nickname is $nickname and password is $password ")

            if (isInputValidated(nickname, password)) {
                val loggedInUser = LoginUseCase.login(nickname, password)
                if(loggedInUser != null) {
                    openHomePage(loggedInUser)
                }
            }
        }
    }

    // checks if input(nickname, password) are entered correctly
    private fun isInputValidated(nickname: String, password: String): Boolean {
        if(nickname.isEmpty()) {
            displayMessage(R.string.empty_nickname_error)
            return false
        } else if(password.isEmpty()) {
            displayMessage(R.string.empty_password_error)
            return false
        }
        return true
    }

    private fun displayMessage(messageResourceId: Int) {
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show()
    }

    private fun setLoginPageSignUpButtonListener() {
        activityMainBinding.loginPageSignUpButton.setOnClickListener {
            openSignUpPage()
        }
    }

    private fun openSignUpPage() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun openHomePage(user: UserModel) {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra(ExtraKeys.LOGGED_IN_USER, user as Serializable)
        startActivity(intent)
    }
}
