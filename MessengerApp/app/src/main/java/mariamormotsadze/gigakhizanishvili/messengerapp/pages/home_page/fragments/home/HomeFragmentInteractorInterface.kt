package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.ChatModel

interface HomeFragmentInteractorInterface {
    fun getChatHistory(): List<ChatModel>
}
