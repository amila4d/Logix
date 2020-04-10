package com.logistics.logix.ui.consignmentDetails

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.ConsignmentDetail
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User
import kotlinx.android.synthetic.main.fragment_consignment_details.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import java.text.SimpleDateFormat
import java.util.*

class ConsignmentDetailsFragment : Fragment() {

    private val modelPreferences: ModelPreferences = get()

    private lateinit var consignmentDetailsViewModel: ConsignmentDetailsViewModel

    private var etDate: EditText? = null
    private var btnSubmit: Button? = null
    private var selectedDate: String = ""
    internal lateinit var context: Context
    private lateinit var user: User

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        consignmentDetailsViewModel =
                ViewModelProviders.of(this).get(ConsignmentDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_consignment_details, container, false)
        context = this.activity!!
        initializations(root)
        return root
    }


    private fun initializations(view: View){
        etDate = view.findViewById(R.id.etDate)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        user = modelPreferences.getObject("user", User::class.java)!!

        val cal = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, 0)
        selectedDate = df.format(cal.time)


        etDate?.setOnClickListener {
            val cal = Calendar.getInstance()
            if(selectedDate.isNotEmpty()) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val date = sdf.parse(selectedDate)
                cal.time = date
            }

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpdTo = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { view, year, month,
                                                     day ->
                    selectedDate = "" + year + "-" + (month+1) + "-" + day
                    etDate!!.setText(getCurrentDate(selectedDate))
                }, year, month, day)

            dpdTo.datePicker.minDate = System.currentTimeMillis() - 1000
            dpdTo.show()
        }

        btnSubmit!!.setOnClickListener {
            if(etDeploymentDestination.text.toString().isNotEmpty() &&
                etContainerId.text.toString().isNotEmpty() &&
                etTypeOfGoodsTransported.text.toString().isNotEmpty() &&
                etDate!!.text.toString().isNotEmpty() &&
                etFreightForwarderAssociated.text.toString().isNotEmpty() &&
                    user.userTypeId != null) {

                val consignmentDetail = ConsignmentDetail(0, etContainerId.text.toString(),
                        etDeploymentDestination.text.toString(),
                    etTypeOfGoodsTransported.text.toString(),
                    etDate!!.text.toString(),
                    etFreightForwarderAssociated.text.toString(),
                    user.id)
                consignmentDetailsViewModel.insertConsignmentDetail(consignmentDetail)
                showAlert()
            }else{
                Toast.makeText(context, "Please enter all consignment details",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Form Submitted!")
        builder.setMessage("Consignment detail entered into profile..")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Ok"){ dialogInterface, which ->
            findNavController().popBackStack(R.id.nav_consignment_details, true);
            findNavController().navigate(R.id.nav_home_shipping_container_company_home)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun getCurrentDate(dateAndTme: String): String {
        val df = SimpleDateFormat("yyyy-M-d", Locale.US)
        val result: Date
        result = df.parse(dateAndTme)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(result)
    }

}
