
package mariamormotsadze.gigakhizanishvili.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityMainBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.login.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        Log.i("LoginPage", "here??")
        setListeners()
    }

    private fun setListeners(){
        activityMainBinding.signInButton.setOnClickListener{
            Log.i("LoginPage", "sign in button clicked")
            val nickname = activityMainBinding.loginNicknameTextField.text.toString()
            val password = activityMainBinding.loginPasswordTextField.text.toString()
            validateInput(nickname, password)
            Log.i("LoginPage", "nickname is $nickname and password is $password ")
        }

        activityMainBinding.loginPageSignUpButton.setOnClickListener {
            Log.i("LoginPage", "signup button clicked")
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun validateInput(nickname: String, password: String){
        if(nickname.isEmpty()){
            Toast.makeText(this, R.string.empty_nickname_error, Toast.LENGTH_SHORT).show()
        } else if(password.isEmpty()){
            Toast.makeText(this, R.string.empty_password_error, Toast.LENGTH_SHORT).show()
        }
    }
}
