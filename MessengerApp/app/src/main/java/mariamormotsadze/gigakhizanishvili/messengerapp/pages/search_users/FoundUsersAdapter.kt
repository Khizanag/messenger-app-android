package mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mariamormotsadze.gigakhizanishvili.messengerapp.R

class FoundUsersAdapter(private val initialData: List<FoundUserModel>): RecyclerView.Adapter<FoundUsersAdapter.ViewHolder>() {

    private var data: List<FoundUserModel> = initialData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_user_page_cell, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun configure(with: List<FoundUserModel>) {
        this.data = with
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhotoView: ImageView = itemView.findViewById(R.id.avatar_image_view)
        private val nicknameTextView: TextView = itemView.findViewById(R.id.nickname_text_view)
        private val professionTextView: TextView = itemView.findViewById(R.id.profession_text_view)

        fun bind(item: FoundUserModel) = with(itemView) {
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.avatar_image_placeholder)
                .into(profilePhotoView)
            nicknameTextView.setText(item.nickname)
            professionTextView.setText(item.profession)

            setOnClickListener {
                // TODO: Handle on click
                Log.i("`", "Clicked on what????")
            }
        }
    }
}