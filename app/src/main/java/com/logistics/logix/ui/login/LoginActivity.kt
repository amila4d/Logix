package com.logistics.logix.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout
import com.logistics.logix.ui.MainActivity
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.User
import com.logistics.logix.ui.BaseActivity
import com.logistics.logix.ui.intro.IntroActivity
import com.logistics.logix.ui.register.RegisterActivity
import com.logistics.logix.util.AppHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class LoginActivity : BaseActivity() {

    private val modelPreferences: ModelPreferences = get()

    private lateinit var loginViewModel: LoginViewModel

    internal lateinit var context: Context
    private var btnLogin: Button? = null
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var tvRegister: TextView? = null
    private var tilUserName: TextInputLayout? = null
    private var tilPassword: TextInputLayout? = null
    private var progressBarLogin: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setupActionBar()
        initWidgets()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            setActionBar(context, actionBar, getString(R.string.welcome_to_logix), 24f)
            displayHomeAsUpEnabled(actionBar, false)
        }
    }

    private fun initWidgets() {
        btnLogin = findViewById(R.id.btnSignIn)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        tilUserName = findViewById(R.id.textInputLayoutUsername)
        tilPassword = findViewById(R.id.textInputLayoutPw)
        progressBarLogin = findViewById(R.id.progressBarLogin)
        tvRegister = findViewById(R.id.tvRegister)

        etUsername!!.addTextChangedListener(LoginTextWatcher(etUsername!!))
        etPassword!!.addTextChangedListener(LoginTextWatcher(etPassword!!))
        progressBarLogin!!.visibility = View.INVISIBLE

        tvRegister!!.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {
                putExtra("msg", "msg")
            }
            startActivity(intent)
            finish()
        }

        cbKeepSignedIn.setOnClickListener(View.OnClickListener {
            val sharedPref: SharedPreferences by inject(named(getString(R.string.settings_prefs)))
            if (cbKeepSignedIn.isChecked) {
                val editor: SharedPreferences.Editor =  sharedPref.edit()
                editor.putBoolean(getString(R.string.is_logged_in), true)
                editor.apply()
                editor.commit()
            } else {
                val editor: SharedPreferences.Editor =  sharedPref.edit()
                editor.putBoolean(getString(R.string.is_logged_in), false)
                editor.apply()
                editor.commit()
            }
        })
    }

    fun onClickBtnLogin(view: View) {
        view.id
        if (submitForm()) {
            val username = etUsername!!.text.trim().toString()
            val password = etPassword!!.text.toString()

            if (AppHelper.isInternetAvailable(context)) {
                runBlocking {
                    val user = loginViewModel.getUser(username, password)
                    if (user != null && user.email.isNotEmpty()) {
                        modelPreferences.putObject("user", user)

                        //For intro
                        if(user.active == 0) {
                            user.active = 1
                            loginViewModel.updateUser(user)
                            val dbUser = loginViewModel.getUserById(user.id)
                            modelPreferences.putObject("user", dbUser)
                            callIntroActivity()
                        }else {
                            callMainActivity()
                        }
                    } else
                        Toast.makeText(
                            context,
                            "Email does not in database. Please register first",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.check_internet), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun submitForm(): Boolean {

        if (!validateUserName()) {
            return false
        }

        if (!validatePassword()) {
            return false
        }
        return true
    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("msg", "msg")
        }
        startActivity(intent)
        finish()
    }

    private fun callIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java).apply {
            putExtra("msg", "msg")
        }
        startActivity(intent)
        finish()
    }

    private inner class LoginTextWatcher internal constructor(private val view: View) :
        TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.etUsername -> validateUserName()
                R.id.etPassword -> validatePassword()
            }
        }
    }

    private fun validateUserName(): Boolean {
        if (etUsername!!.text.toString().trim().isEmpty() && etUsername!!.text.isNotBlank()) {
            tilUserName!!.error = getString(R.string.please_type_email)
            requestFocus(etUsername!!)
            return false
        } else if (etUsername!!.text.length < 4) {
            tilUserName!!.error = getString(R.string.email_min_length)
            requestFocus(etUsername!!)
            return false
        } else {
            tilUserName!!.isErrorEnabled = false
        }

        return true
    }

    private fun validatePassword(): Boolean {
        if (etPassword!!.text.toString().trim().isEmpty()) {
            tilPassword!!.error = getString(R.string.please_type_password)
            requestFocus(etPassword!!)
            return false
        } else {
            tilPassword!!.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

}