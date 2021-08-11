package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.profile_page

import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel

interface ProfilePageFragmentControllerInterface {
    fun updateUser(user: UserModel)
    fun signOut()
}