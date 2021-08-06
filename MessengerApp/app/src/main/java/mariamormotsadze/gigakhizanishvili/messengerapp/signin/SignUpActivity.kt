package mariamormotsadze.gigakhizanishvili.messengerapp.signin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySignUpBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants.Companion.PICK_IMAGE
import mariamormotsadze.gigakhizanishvili.messengerapp.homepage.HomePageActivity

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
        activitySignUpBinding.signUpButton.setOnClickListener {
            val nickname = activitySignUpBinding.signUpNicknameTextField.text.toString()
            val password = activitySignUpBinding.signUpPasswordTextField.text.toString()
            val profession = activitySignUpBinding.whatIDo.text.toString()
            val isValid = validateInput(nickname, password, profession)
            if(isValid){
                val intent = Intent(this, HomePageActivity::class .java)
                startActivity(intent)
            }
        }

        activitySignUpBinding.signUpAvatar.setOnClickListener(){
            Log.i("SignupPage", "avatar pressed")

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            activitySignUpBinding.signUpAvatar.setImageURI((data!!.data))
        }
    }

    private fun validateInput(nickname: String, password: String, profession: String): Boolean{
        when {
            nickname.isEmpty() -> {
                Toast.makeText(this, R.string.empty_nickname_error, Toast.LENGTH_SHORT).show()
                return false
            }
            password.isEmpty() -> {
                Toast.makeText(this, R.string.empty_password_error, Toast.LENGTH_SHORT).show()
                return false
            }
            profession.isEmpty() -> {
                Toast.makeText(this, R.string.empty_profession_error, Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }
}