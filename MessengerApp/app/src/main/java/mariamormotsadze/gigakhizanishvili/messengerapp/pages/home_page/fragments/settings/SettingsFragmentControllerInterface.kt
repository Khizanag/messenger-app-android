package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.settings

import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel

interface SettingsFragmentControllerInterface {
    fun updateUser(user: UserModel)
    fun signOut()
}