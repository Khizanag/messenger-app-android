package mariamormotsadze.gigakhizanishvili.messengerapp.pages.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mariamormotsadze.gigakhizanishvili.messengerapp.R
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.message.MessageModel

class ChatAdapter(private var messages: List<MessageModel>): RecyclerView.Adapter<MessageViewHolder>(){
    private val SENT = 0
    private val RECEIVED = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        Log.i("VIEWTYPE", viewType.toString())
        val view = if(viewType == SENT){
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_message_cell, parent, false)
        } else{
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.their_message_cell, parent, false)
            }

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        Log.i("AMAS VAKETEB", messages.toString())
        Log.i("POSITION", position.toString())
        val message: MessageModel = messages[position]
        Log.i("ES ARYOFILA", message.text.toString())
        holder.message.text = message.text
        holder.sendTime.text = message.sendTime.toString()

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        Log.i("@@@@@ MESSAGES @@@@@ : ", messages.toString())
        Log.i("@@@@@ POSITION @@@@@ : ", position.toString())
        val message: MessageModel = messages[position]
        Log.i("@@@@@ CHAT ADAPTER @@@@@ : ", message.toString()) // TODO: null ariso
        if(message.amISender)
            return SENT

        return RECEIVED
    }

    fun configure(newMessages: List<MessageModel>) {
        messages = newMessages
        notifyDataSetChanged()
    }

}

class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var message = itemView.findViewById<TextView>(R.id.message_cell)
    var sendTime = itemView.findViewById<TextView>(R.id.message_time)
}
