package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.firebase.FirebaseManager
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.Constants
import mariamormotsadze.gigakhizanishvili.messengerapp.shared.DatabaseConstants

class ProfilePageFragment(
    private val controller: ProfilePageFragmentControllerInterface,
    private val model: UserModel,
) : Fragment() {

    private lateinit var profilePhotoView: ImageView
    private lateinit var nicknameTextField: EditText
    private lateinit var professionTextField: EditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button
    private val storageRef = FirebaseStorage.getInstance().reference
    private var savedUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.RequestCode.PICK_IMAGE){
            profilePhotoView.setImageURI((data!!.data))
            savedUri = data!!.data
            Log.i("FirebaseStorage", "uri: ${data!!.data}")
        }
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
        profilePhotoView.setOnClickListener { openGallery() }

        val imageRef = storageRef.child("${DatabaseConstants.IMAGES}/${model.imageName}")

        val task = imageRef.downloadUrl
        task.addOnSuccessListener {
            Glide.with(view.context)
                .load(it)
                .placeholder(R.drawable.avatar_placeholder)
                .into(profilePhotoView)
        }.addOnFailureListener{ }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, Constants.RequestCode.PICK_IMAGE)
    }

    private fun setupNicknameField(view: View, savedInstanceState: Bundle?) {
        nicknameTextField = view.findViewById(R.id.nickname_text_view)
        nicknameTextField.setText(model.nickname)
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
        updateProfession()
        controller.updateUser(model)
    }

    private fun updateProfilePhoto() {}

    private fun updateProfession() {
        model.profession = professionTextField.text.toString()
    }

    private fun updateRemoteUser() {
        val userRef = FirebaseManager.getSignedInUserReference()
        userRef.child("nickname").setValue(model.nickname)
        userRef.child("profession").setValue(model.profession)
        updateProfilePhotoRemotely(userRef)
    }

    private fun updateProfilePhotoRemotely(userRef: DatabaseReference) {
        userRef.child("imageName").setValue(model.imageName)
        val imageRef = storageRef.child("images/" + model.imageName)
        savedUri?.let { imageRef.putFile(it) }
    }

    private fun setupSignOutButton(view: View, savedInstanceState: Bundle?) {
        signOutButton = view.findViewById(R.id.sign_out_button)
        signOutButton.setOnClickListener { controller.signOut() }
    }
}