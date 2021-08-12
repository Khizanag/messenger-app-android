package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel

class HomeFragment(val user: UserModel) : Fragment() {

    var onChatItemClick: ((ChatRowModel) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View){
        val chatsRecyclerView: RecyclerView = view.findViewById(R.id.chats_recycler_view)
        val adapter = ChatsAdapter(user.chats!!.map { pair ->
            val chat = pair.value
            ChatRowModel(chat.otherUser, chat.messages.last().text, chat.messages.last().sendTime!!)
        })
        adapter.onItemClick = onChatItemClick
        chatsRecyclerView.adapter = adapter
    }
}