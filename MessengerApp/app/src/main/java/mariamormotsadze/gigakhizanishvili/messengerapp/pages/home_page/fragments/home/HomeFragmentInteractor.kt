package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home


import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.chat.ChatModel

class HomeFragmentInteractor: HomeFragmentInteractorInterface {
    override fun getChatHistory(): List<ChatModel> {
        return listOf<ChatModel>()
    }
}