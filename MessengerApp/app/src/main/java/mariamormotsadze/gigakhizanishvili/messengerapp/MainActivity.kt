
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
    private lateinit var nicknameTextField: EditText
    private lateinit var passwordTextField: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
//    private lateinit var context: Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        Log.i("LoginPage", "here??")
        init()
    }

    private fun init(){
        initTextFields()
        initButtons()
        Log.i("LoginPage", "here")
        setListeners()
    }

    private fun initTextFields(){
        nicknameTextField = findViewById(R.id.login_nickname_text_field)
        passwordTextField = findViewById(R.id.login_password_text_field)
    }

    private fun initButtons(){
        signInButton = findViewById(R.id.sign_in_button)
        signUpButton = findViewById(R.id.login_page_sign_up_button)
    }

    private fun setListeners(){
        signInButton.setOnClickListener{
            Log.i("LoginPage", "sign in button clicked")
            val nickname = nicknameTextField.text.toString()
            val password = passwordTextField.text.toString()
            validateInput(nickname, password)
            Log.i("LoginPage", "nickname is $nickname and password is $password ")
        }

        signUpButton.setOnClickListener {
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
