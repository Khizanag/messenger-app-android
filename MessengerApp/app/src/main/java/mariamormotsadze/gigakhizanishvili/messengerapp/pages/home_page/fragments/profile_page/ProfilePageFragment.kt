package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants

class ProfilePageFragment(
    private val controller: ProfilePageFragmentControllerInterface,
    private val model: UserModel,
) : Fragment() {

    private lateinit var profilePhotoView: ImageView
    private lateinit var nicknameTextField: TextView
    private lateinit var professionTextField: EditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_profile_page, container, false)
        profilePhotoView = view.findViewById<View>(R.id.user_avatar) as ImageView

        profilePhotoView.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, Constants.PICK_IMAGE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            profilePhotoView.setImageURI((data!!.data))
            // TODO save this profile photo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view, savedInstanceState)
    }

    private fun setup(view: View, savedInstanceState: Bundle?) {
        setupProfilePhoto(view, savedInstanceState)
        setupNicknameField(view, savedInstanceState)
        setupProfessionTextField(view, savedInstanceState)
        setupUpdateButton(view, savedInstanceState)
        setupSignOutButton(view, savedInstanceState)
    }
    private fun setupProfilePhoto(view: View, savedInstanceState: Bundle?) {
        profilePhotoView = view.findViewById(R.id.user_avatar)
        Glide.with(view.context)
            .load(model.imageUrl)
            .placeholder(R.drawable.avatar_placeholder)
            .into(profilePhotoView)
    }

    private fun setupNicknameField(view: View, savedInstanceState: Bundle?) {
        nicknameTextField = view.findViewById(R.id.nickname_text_view)
        nicknameTextField.text = model.nickname
    }

    private fun setupProfessionTextField(view: View, savedInstanceState: Bundle?) {
        professionTextField = view.findViewById(R.id.profession_text_view)
        professionTextField.setText(model.profession)
    }

    private fun setupUpdateButton(view: View, savedInstanceState: Bundle?) {
        updateButton  = view.findViewById(R.id.update_button)
        updateButton.setOnClickListener { saveChanges() }
    }

    private fun saveChanges() {
        updateLocalUser()
        updateRemoteUser()
    }

    private fun updateLocalUser() {
        updateProfilePhoto()
        updateNickname()
        updateProfession()
        controller.updateUser(model)
    }

    private fun updateProfilePhoto() {
        // TODO
    }

    private fun updateNickname() {
        model.nickname = nicknameTextField.text.toString()
    }

    private fun updateProfession() {
        model.profession = professionTextField.text.toString()
    }

    private fun updateRemoteUser() {
        val userRef = FirebaseManager.getSignedInUserReference()
        userRef.child("nickname").setValue(model.nickname)
        userRef.child("profession").setValue(model.profession)
        // TODO: update remote photo
    }

    private fun setupSignOutButton(view: View, savedInstanceState: Bundle?) {
        signOutButton = view.findViewById(R.id.sign_out_button)
        signOutButton.setOnClickListener { controller.signOut() }
    }
}