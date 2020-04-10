package com.logistics.logix.ui.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.logistics.logix.R
import com.logistics.logix.ui.freightForwarderMapActivity.FreightForwarderMapsActivityV2
import kotlinx.android.synthetic.main.fragment_notification.view.*

class NotificationFragment : Fragment() {

    private lateinit var notificationViewModel: NotifictionViewModel
    internal lateinit var context: Context

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotifictionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notification, container, false)
        context = this.activity!!

        root.btnMap!!.setOnClickListener {
            val intent = Intent(context, FreightForwarderMapsActivityV2::class.java).apply {
                putExtra("msg", "msg")
            }
            startActivity(intent)
        }

        return root
    }
}
