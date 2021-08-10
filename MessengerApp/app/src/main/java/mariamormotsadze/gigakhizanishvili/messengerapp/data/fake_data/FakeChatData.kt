package mariamormotsadze.gigakhizanishvili.messengerapp.data.fake_data

import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.ChatModel
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.MessageModel
import java.time.Instant
import java.util.*

object FakeChatData {

    fun getMessages1() = mutableListOf(
        MessageModel("1", "1", "2", Date.from(Instant.MIN),"Hello User 1"),
        MessageModel("2", "2", "1", Date.from(Instant.MIN),"Hello User 2"),
        MessageModel("3", "1", "2", Date.from(Instant.MIN),"this is a very good aplication"),
        MessageModel("4", "1", "2", Date.from(Instant.MIN),"what do you think"),
        MessageModel("5", "2", "1", Date.from(Instant.MIN),"Yes the best I've ever seen"),
    )

    fun getChat1() = ChatModel("1", "1", "2", getMessages1())

    fun getChat2() = ChatModel("2", "1", "3", getMessages1())

    fun getChats() = listOf( getChat1(), getChat2(), )
}