package mariamormotsadze.gigakhizanishvili.messengerapp.pages.sign_in

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserServiceModel
import mariamormotsadze.gigakhizanishvili.messengerapp.databinding.ActivitySignUpBinding
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.HomePageActivity
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import kotlin.concurrent.thread

class SignUpActivity : AppCompatActivity() {

    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private val storageRef = FirebaseStorage.getInstance().reference

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

    private fun setupAvatar() {}

    private fun setupSignUpButton() {
        activitySignUpBinding.signUpButton.setOnClickListener {
            val nickname = activitySignUpBinding.nicknameTextView.text.toString()
            val password = activitySignUpBinding.signUpPasswordTextField.text.toString()
            val profession = activitySignUpBinding.whatIDo.text.toString()
            if (isInputValid(nickname, password, profession)) {
                signUp(nickname, password, profession)
            }
        }
    }

    private fun signUp(nickname: String, password: String, profession: String) {
        val auth = Firebase.auth
        val email = "$nickname@gmail.com"
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("`firebase`s", "createUserWithEmail:success")
                    auth.currentUser?.let { firebaseUser ->
                        val userId = firebaseUser.uid
                        val defaultImageName = userId
                        val chats = hashMapOf(
                            "administration_id" to ChatServiceModel(
                                    listOf(
                                        MessageModel(null, "${SimpleDateFormat()}", Constants.GREETINGS_MESSAGE_TEXT, false)
                                    )
                                )
                        )
                        val serviceUser = UserServiceModel(nickname, defaultImageName, profession, chats)
                        insertUserIntoDatabase(serviceUser, userId)
                        thread { uploadPlaceholderProfileImage(defaultImageName) }
                        openHomePage()
                    }
                } else {
                    showMessage(R.string.sign_up_error)
                }
            }
    }

    private fun uploadPlaceholderProfileImage(imageName: String) {
        val imagesRef = storageRef.child("images/${imageName}")
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_placeholder)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()
        imagesRef.putBytes(data)
    }

    private fun insertUserIntoDatabase(user: UserServiceModel, userId: String) {
        val database = Firebase.database
        val usersRef = database.getReference(DatabaseConstants.USERS)
        usersRef.push().key?.let {
            usersRef.child(userId).setValue(user)
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

    private fun openHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }
}