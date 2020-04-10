package com.logistics.logix.ui.splash

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.logistics.logix.ui.MainActivity
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.ui.login.LoginActivity
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class SplashActivity : AppCompatActivity() {

    //private val retrofitFactory: RetrofitFactoryService = get()
    private val modelPreferences: ModelPreferences = get()

    // Splash screen timer
    private val SPLASH_TIME_OUT = 1000L

    internal lateinit var context: Context
    private lateinit var splashViewModel: SplashViewModel

    private val animator = ValueAnimator.ofFloat(1.0f, 0.0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this

        splashViewModel =
            ViewModelProviders.of(this).get(SplashViewModel::class.java)

        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 5000L
        runBlocking {
            val sharedPref: SharedPreferences by inject(named(getString(R.string.settings_prefs)))

            val isLoggedIn =
                sharedPref.getBoolean(getString(R.string.is_logged_in), false)

            val userList = splashViewModel.getAllUser()
            Handler().postDelayed(
                {
                if(userList.isEmpty() || !isLoggedIn) {
                    callLoginActivity()
                }else{
                    callMainActivity()
                }
                }, SPLASH_TIME_OUT)
        }

    }

    private fun setLoggingStatus(isLoggedInConfirm: Boolean){
        val sharedPref: SharedPreferences by inject(named(getString(R.string.settings_prefs)))
        val editor: SharedPreferences.Editor =  sharedPref.edit()
        editor.putBoolean(getString(R.string.is_logged_in), isLoggedInConfirm)
        editor.apply()
        editor.commit()
    }

    private fun getSplashScreenDuration() = 2000L

    private fun callLoginActivity(){
        setLoggingStatus(false)
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("msg", "msg")
        }
        startActivity(intent)
        finish()
    }

    private fun callMainActivity(){
        setLoggingStatus(true)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("msg", "msg")
        }
        startActivity(intent)
        finish()
    }

}