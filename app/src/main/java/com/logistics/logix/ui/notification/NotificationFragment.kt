package com.logistics.logix.ui.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.Notification
import com.logistics.logix.ui.freightForwarderMapActivity.FreightForwarderMapsActivityV2
import kotlinx.android.synthetic.main.fragment_notification.view.*
import org.koin.android.ext.android.get

class NotificationFragment : Fragment() {
    private val modelPreferences: ModelPreferences = get()
    private lateinit var notificationViewModel: NotifictionViewModel
    internal lateinit var context: Context
    private lateinit var tvTitle: TextView
    private lateinit var tvBody: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotifictionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notification, container, false)
        context = this.requireActivity()
        tvTitle = root.findViewById(R.id.tvTitle)
        tvBody = root.findViewById(R.id.tvBody)

        if(arguments?.getString("title") != null && requireArguments().getString("title")!!.isNotEmpty()) {
            tvTitle.text = "Notification title: "+arguments?.getString("title")
            tvBody.text = "Message: "+arguments?.getString("body")
        }else{
            val notification = modelPreferences.getObject("notification", Notification::class.java)
            if(notification != null) {
                tvTitle.text = "Notification title: " + notification!!.title
                tvBody.text = "Message: " + notification!!.content
            }
        }

        root.btnMap!!.setOnClickListener {
            val intent = Intent(context, FreightForwarderMapsActivityV2::class.java).apply {
                putExtra("msg", "msg")
            }
            startActivity(intent)
        }

        return root
    }
}
