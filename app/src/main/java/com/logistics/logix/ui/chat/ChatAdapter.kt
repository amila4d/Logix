package com.logistics.logix.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.logistics.logix.R
import kotlinx.android.synthetic.main.list_item_chat.view.*


class ChatAdapter (var chatList: MutableList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.UserViewHolder>() {
    fun ChatAdapter(details: List<Chat>) {
        chatList.clear()
        chatList.addAll(details)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_chat, parent, false)
    )
    override fun getItemCount() = chatList.size
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(chatList[position])
    }
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvText = view.tvChatBubble

        fun bind(consignmentDetail: Chat) {
            tvText.text = consignmentDetail.text
        }
    }
}