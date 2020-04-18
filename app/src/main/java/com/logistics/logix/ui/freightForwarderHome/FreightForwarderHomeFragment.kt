package com.logistics.logix.ui.freightForwarderHome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
        val btnSearchContainer : Button = root.findViewById(R.id.btnSearchContainer)
        btnSearchContainer.setOnClickListener {
            findNavController().navigate(R.id.nav_search)
        }
        return root
    }
}
