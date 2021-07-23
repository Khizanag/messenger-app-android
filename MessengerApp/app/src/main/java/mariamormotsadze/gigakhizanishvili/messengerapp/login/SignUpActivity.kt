package mariamormotsadze.gigakhizanishvili.messengerapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivityMainBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var activitySignUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)
        setListeners()
    }

    private fun setListeners(){
        activitySignUpBinding.signUpButton.setOnClickListener(){
            val nickname = activitySignUpBinding.signUpNicknameTextField.text.toString()
            val password = activitySignUpBinding.signUpPasswordTextField.text.toString()
            val profession = activitySignUpBinding.whatIDo.text.toString()
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