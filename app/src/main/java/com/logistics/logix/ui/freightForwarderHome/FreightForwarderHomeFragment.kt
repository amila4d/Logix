package com.logistics.logix.ui.freightForwarderHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.logistics.logix.R

class FreightForwarderHomeFragment : Fragment() {

    private lateinit var freightForwarderHomeViewModel: FreightForwarderHomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        freightForwarderHomeViewModel =
                ViewModelProviders.of(this).get(FreightForwarderHomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_freight_forwarder_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        /*freightForwarderHomeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}
