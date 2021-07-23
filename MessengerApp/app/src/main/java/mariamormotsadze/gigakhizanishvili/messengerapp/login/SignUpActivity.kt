package mariamormotsadze.gigakhizanishvili.messengerapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import mariamormotsadze.gigakhizanishvili.messengerapp.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var nicknameTextField: EditText
    private lateinit var passwordTextField: EditText
    private lateinit var professionTextField: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()
    }

    private fun init(){
        initTextFields()
        initButtons()
        Log.i("LoginPage", "here")
    }

    private fun initTextFields(){
        nicknameTextField = findViewById(R.id.login_nickname_text_field)
        passwordTextField = findViewById(R.id.login_password_text_field)
        professionTextField = findViewById(R.id.what_i_do)
    }

    private fun initButtons(){
        signUpButton = findViewById(R.id.sign_up_button)
        signUpButton.setOnClickListener(){
            val nickname = nicknameTextField.text.toString()
            val password = passwordTextField.text.toString()
            val profession = professionTextField.text.toString()
            validateInput(nickname, password, profession)
        }
    }

    private fun validateInput(nickname: String, password: String, profession: String){
        if(nickname.isEmpty()){
            Toast.makeText(this, R.string.empty_nickname_error, Toast.LENGTH_SHORT).show()
        } else if(password.isEmpty()){
            Toast.makeText(this, R.string.empty_password_error, Toast.LENGTH_SHORT).show()
        } else if(profession.isEmpty()){
            Toast.makeText(this, R.string.empty_profession_error, Toast.LENGTH_SHORT).show()
        }
    }
}