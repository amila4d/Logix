package com.logistics.logix.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.androidbuts.multispinnerfilter.SpinnerListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.User
import com.logistics.logix.database.model.UserType
import com.logistics.logix.ui.login.LoginActivity
import com.logistics.logix.ui.shippingContainerCompanyHome.ShippingContainerCompanyHomeViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get

class SettingsFragment : Fragment() {

    private val modelPreferences: ModelPreferences = get()

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var user: User
    private var btnRegister: Button? = null
    private var etFullName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmPw: EditText? = null
    private var textInputConfirmPw: TextInputLayout? = null
    private var textInputPassword: TextInputLayout? = null
    private var textInputFullName: TextInputLayout? = null
    private var textInputEmail: TextInputLayout? = null
    internal lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        //user = modelPreferences.getObject("user", User::class.java)!!
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        context = activity!!
        initWidgets(root)
        return root
    }

    private fun initWidgets(root: View) {
        btnRegister = root.findViewById(R.id.btnRegister)
        etFullName = root.findViewById(R.id.etFullName)
        etEmail = root.findViewById(R.id.etEmail)
        etPassword = root.findViewById(R.id.etPassword)
        etConfirmPw = root.findViewById(R.id.etConfirmPw)
        textInputEmail = root.findViewById(R.id.textInputEmail)
        textInputFullName = root.findViewById(R.id.textInputFullName)
        textInputPassword = root.findViewById(R.id.textInputPassword)
        textInputConfirmPw = root.findViewById(R.id.textInputConfirmPw)

        etFullName!!.addTextChangedListener(LoginTextWatcher(etFullName!!))
        etEmail!!.addTextChangedListener(LoginTextWatcher(etEmail!!))
        etPassword!!.addTextChangedListener(LoginTextWatcher(etPassword!!))
        etConfirmPw!!.addTextChangedListener(LoginTextWatcher(etConfirmPw!!))


        user = modelPreferences.getObject("user", User::class.java)!!
        etEmail!!.setText(user.email)
        etFullName!!.setText(user.fullName)
        etPassword!!.setText(user.password)
        etConfirmPw!!.setText(user.password)

        btnRegister!!.text = "Update"
        btnRegister!!.setOnClickListener {
            if (submitForm()) {
                runBlocking {
                    user.email = etEmail!!.text.toString()
                    user.fullName = etFullName!!.text.toString()
                    user.password = etPassword!!.text.toString()
                    settingsViewModel.updateUser(user)
                    val dbUser = settingsViewModel.getUserById(user.id)
                    modelPreferences.putObject("user", dbUser)
                    user = modelPreferences.getObject("user", User::class.java)!!

                    Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show()

                    //findNavController().popBackStack(R.id.nav_search, false)
                    /*if (user.userTypeId == 1) {
                        findNavController().navigate(R.id.nav_home_freight_forwarder_home)
                    } else if (user.userTypeId == 2) {
                        findNavController().navigate(R.id.nav_home_shipping_container_company_home)
                    } else if (user.userTypeId == 3) {
                        findNavController().navigate(R.id.nav_home_importer_exporter_home)
                    }*/
                }
                /*val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("msg", "msg")
                }
                startActivity(intent)
                context.finish()*/
            }
        }
    }

    private fun submitForm(): Boolean {
        if (!validateFullName()) {
            return false
        }
        if (!validateEmail()) {
            return false
        }
        if (!validatePassword()) {
            return false
        }
        if (!validateConfirmPassword()) {
            return false
        }
        return true
    }

    private inner class LoginTextWatcher internal constructor(private val view: View) :
        TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.etFullName -> validateFullName()
                R.id.etEmail -> validateEmail()
                R.id.etPassword -> validatePassword()
                R.id.etConfirmPw -> validateConfirmPassword()
            }
        }
    }

    private fun validateEmail(): Boolean {
        if (etEmail!!.text.toString().trim().isEmpty() && etEmail!!.text!!.isNotBlank()) {
            textInputEmail!!.error = getString(R.string.please_type_email)
            requestFocus(etEmail!!)
            return false
        } else if (etEmail!!.text!!.length < 4) {
            textInputEmail!!.error = getString(R.string.email_min_length)
            requestFocus(etEmail!!)
            return false
        } else {
            textInputEmail!!.isErrorEnabled = false
        }

        return true
    }

    private fun validateFullName(): Boolean {
        if (etFullName!!.text.toString().trim().isEmpty()) {
            textInputFullName!!.error = getString(R.string.please_type_full_name)
            requestFocus(etFullName!!)
            return false
        } else {
            textInputFullName!!.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (etPassword!!.text.toString().trim().isEmpty()) {
            textInputPassword!!.error = getString(R.string.please_type_password)
            requestFocus(etPassword!!)
            return false
        } else {
            textInputPassword!!.isErrorEnabled = false
        }
        return true
    }

    private fun validateConfirmPassword(): Boolean {
        if (etConfirmPw!!.text.toString().trim().isEmpty()) {
            textInputConfirmPw!!.error = getString(R.string.please_type_password_again)
            requestFocus(etConfirmPw!!)
            return false
        }else if(etPassword!!.text.toString().trim().isNotEmpty() &&
            etPassword!!.text.toString() != etConfirmPw!!.text.toString()
        ){
            textInputConfirmPw!!.error = "Password did not match"
            requestFocus(etConfirmPw!!)
            return false
        }
        else {
            textInputConfirmPw!!.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

}