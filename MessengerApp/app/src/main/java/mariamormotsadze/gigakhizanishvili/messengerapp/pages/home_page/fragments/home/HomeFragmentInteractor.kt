package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import mariamormotsadze.gigakhizanishvili.messengerapp.data.fake_data.FakeChatData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.ChatModel

class HomeFragmentInteractor: HomeFragmentInteractorInterface {
    override fun getChatHistory(): List<ChatModel> {
        return FakeChatData.getChats()
    }
}