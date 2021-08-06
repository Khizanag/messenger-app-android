
package mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat.ChatActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityMainBinding

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

            if (isInputValidated(nickname, password)) {
                val intent = Intent(this, ChatActivity::class .java)
                startActivity(intent)

                Log.i("LoginPage", "nickname is $nickname and password is $password ")
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
            Log.i("LoginPage", "signup button clicked")
            openSignUpPage()
        }
    }

    private fun openSignUpPage() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun validateInput(nickname: String, password: String){

    }
}
