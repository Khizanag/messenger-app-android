
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
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.SignInUseCase
import java.io.Serializable

class SignInActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupBinding()
        setupTextFields()
        setupButtons()
    }

    private fun setupTextFields() {
        setupNicknameTextField()
        setupPasswordTextField()
    }

    private fun setupButtons() {
        setupSignInButton()
        setupSignUpButton()
    }

    private fun setupNicknameTextField() {
        activityMainBinding.loginNicknameTextField.setText("")
    }

    private fun setupPasswordTextField() {
        activityMainBinding.loginPasswordTextField.setText("")
    }

    private fun setupBinding() {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

    private fun setupSignInButton() {
        activityMainBinding.signInButton.setOnClickListener{
            val nickname = activityMainBinding.loginNicknameTextField.text.toString()
            val password = activityMainBinding.loginPasswordTextField.text.toString()

            if (isInputValid(nickname, password)) {
                val loggedInUser = SignInUseCase.signIn(nickname, password)
                if(loggedInUser != null) {
                    openHomePage(loggedInUser)
                }
            }
        }
    }

    // checks if input(nickname, password) are entered correctly
    private fun isInputValid(nickname: String, password: String): Boolean {
        when {
            nickname.isEmpty() -> { showMessage(R.string.empty_nickname_error); return false }
            password.isEmpty() -> { showMessage(R.string.empty_password_error); return false }
        }
        return true
    }

    private fun showMessage(messageResourceId: Int) {
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show()
    }

    private fun setupSignUpButton() {
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
//        reset()
    }

    private fun reset() {
        setupTextFields()
    }
}
