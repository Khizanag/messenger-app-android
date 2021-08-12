
package mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityMainBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.HomePageActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isUserSignedIn()) {
            openHomePage()
        } else {
            setup()
        }
    }

    private fun isUserSignedIn() = Firebase.auth.currentUser != null

    private fun setup() {
        setupBinding()
        setupDatabase()
        setupTextFields()
        setupButtons()
    }

    private fun setupDatabase() {
        auth = Firebase.auth
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

            Log.i("`login`", "nickname: $nickname, password: $password")

            if (isInputValid(nickname, password)) {
                signIn(nickname, password)
            } // else is handled in isInputValid
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

    private fun openHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }

    private fun signIn(nickname: String, password: String) {
        val email = "$nickname@gmail.com"
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser != null) {
                        openHomePage()
                    } else {
                        showMessage(R.string.sign_in_error)
                    }
                } else {
                    showMessage(R.string.sign_in_error)
                }
            }
    }
}
