package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users.FoundUserModel

class ChatsAdapter(private val initialData: List<ChatRowModel>): RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    var onItemClick: ((ChatRowModel) -> Unit)? = null

    private var data: List<ChatRowModel> = initialData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_user_page_cell, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun configure(with: List<ChatRowModel>) {
        this.data = with
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhotoView: ImageView = itemView.findViewById(R.id.avatar_image_view)
        private val nicknameTextView: TextView = itemView.findViewById(R.id.nickname_text_view)
        private val professionTextView: TextView = itemView.findViewById(R.id.profession_text_view)

        fun bind(model: ChatRowModel) = with(itemView) {
            Glide.with(itemView.context)
                .load(model.profilePhoto)
                .placeholder(R.drawable.avatar_placeholder)
                .into(profilePhotoView)
            nicknameTextView.text = model.nickname
//            professionTextView.text = model.profession
            setOnClickListener {
                onItemClick?.invoke(model)
            }
        }
    }
}