package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.ChatModel

object ChatRowModelConverter {
    fun convert(model: ChatModel, currentUserId: String?): ChatRowModel {
        val secondUserId = if (currentUserId == model.firstUserId) model.secondUserId else model.firstUserId
        return ChatRowModel(model.id, )
    }
}