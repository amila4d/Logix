package com.logistics.logix.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.android.ext.android.get

class ChatFragment : Fragment() {

    private val modelPreferences: ModelPreferences = get()
    internal lateinit var context: Context
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnSend: Button
    private var mAdapter: ChatAdapter? = null
    private lateinit var chatList: ArrayList<Chat>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chat, container, false)
        context = this.activity!!
        recyclerView = root.findViewById(R.id.recyclerView)
        btnSend = root.findViewById(R.id.btnSend)
        chatList = ArrayList()
        btnSend.setOnClickListener{
            if(etText.text != null && etText.text.isNotEmpty()) {
                val chat = Chat(0, etText.text.toString())
                chatList.add(chatList.size, chat)
                loadChat()
                mAdapter!!.notifyItemInserted(chatList.size)
            }else{
                Toast.makeText(context, "Please enter meesage",Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun loadChat(){
        mAdapter = ChatAdapter(chatList.toMutableList())
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.invalidateItemDecorations()
        recyclerView.adapter = mAdapter
    }


}