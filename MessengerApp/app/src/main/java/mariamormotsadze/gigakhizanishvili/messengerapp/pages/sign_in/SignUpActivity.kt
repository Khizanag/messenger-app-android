package mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySignUpBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.HomePageActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.ExtraKeys
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.SignInUseCase
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases.SignUpUseCase
import java.io.Serializable

class SignUpActivity : AppCompatActivity() {

    private lateinit var activitySignUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupBinding()
        setupAvatar()
        setupSignUpButton()
    }

    // should happen firstly: before doing other things
    private fun setupBinding() {
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)
    }

    private fun setupAvatar() {
        activitySignUpBinding.signUpAvatar.setOnClickListener(){
            Log.i("SignupPage", "avatar pressed")

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, Constants.PICK_IMAGE)
            // TODO save chosen image
        }
    }

    private fun setupSignUpButton() {
        activitySignUpBinding.signUpButton.setOnClickListener {
            val nickname = activitySignUpBinding.nicknameTextView.text.toString()
            val password = activitySignUpBinding.signUpPasswordTextField.text.toString()
            val profession = activitySignUpBinding.whatIDo.text.toString()
            if (isInputValid(nickname, password, profession)){
                if(SignUpUseCase.signUp(nickname, password, profession)) {
                    val loggedInUser = SignInUseCase.signIn(nickname, password)
                    if(loggedInUser != null) {
                        openHomePage(loggedInUser)
                    }
                } else {
                    showMessage(R.string.sign_up_error)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            activitySignUpBinding.signUpAvatar.setImageURI((data!!.data))
        }
    }

    private fun isInputValid(nickname: String, password: String, profession: String): Boolean {
        when {
            nickname.isEmpty() -> { showMessage(R.string.empty_nickname_error); return false }
            password.isEmpty() -> { showMessage(R.string.empty_password_error); return false  }
            profession.isEmpty() -> { showMessage(R.string.empty_profession_error); return false  }
        }

        return true
    }

    private fun showMessage(messageId: Int) {
        Toast.makeText(this, R.string.empty_profession_error, Toast.LENGTH_SHORT).show()
    }

    private fun openHomePage(user: UserModel) {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra(ExtraKeys.LOGGED_IN_USER, user as Serializable)
        startActivity(intent)
    }
}