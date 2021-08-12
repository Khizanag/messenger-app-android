package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mariamormotsadze.gigakhizanishvili.messengerapp.R

class ChatsAdapter(private val initialData: List<ChatRowModel>): RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    var onItemClick: ((ChatRowModel) -> Unit)? = null

    private var data: List<ChatRowModel> = initialData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_page_chat_cell, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun configure(with: List<ChatRowModel>) {
        this.data = with
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhotoView: ImageView = itemView.findViewById(R.id.main_page_avatar)
        private val nicknameTextView: TextView = itemView.findViewById(R.id.nickname_text_view)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.main_page_message_preview)
        private val lastMessageTimeTextView: TextView = itemView.findViewById(R.id.time_from_last_message_text_view)

        fun bind(model: ChatRowModel) = with(itemView) {
            Glide.with(itemView.context)
                .load(model.otherUser.imageUrl)
                .placeholder(R.drawable.avatar_placeholder)
                .into(profilePhotoView)
            nicknameTextView.text = model.otherUser.nickname

            lastMessageTextView.text = model.lastMessage
            lastMessageTimeTextView.text = model.timeFromLastMessage

            setOnClickListener {
                onItemClick?.invoke(model)
            }
        }
    }
}