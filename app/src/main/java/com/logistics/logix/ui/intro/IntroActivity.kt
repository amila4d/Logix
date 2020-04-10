package com.logistics.logix.ui.intro

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.User
import com.logistics.logix.ui.MainActivity
import com.logistics.logix.ui.MainViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class IntroActivity : AppIntro() {
    private lateinit var introViewModel: IntroViewModel
    private val modelPreferences: ModelPreferences = get()

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        introViewModel =
            ViewModelProviders.of(this).get(IntroViewModel::class.java)

        user = modelPreferences.getObject("user", User::class.java)!!

        var description1: String = ""
        var description2: String = ""
        var description3: String = ""
        var description4: String = ""
        if(user.userTypeId == 1) {
            description1 = "Follow the three-step guide to view what Logix offers to Freight Forwarders"
            description2 = "Search for your preferred container from a variety of reputed Shipping container companies for utilization"
            description3 = "Communicate with different companies and start shipment of your goods with no extra hassle"
            description4 = "View empty containers in the Map and set and organize your location to start consignment"
        }else if(user.userTypeId == 2){
            description1 = "Follow the three-step guide to view what Logix offers to Shipping container company’s"
            description2 = "Communicate with different Freight Forwarders and find consignments with no hassle"
            description3 = "Locate empty containers with ease"
            description4 = "Interact with Importers/Exporters with ease"
        }else if(user.userTypeId == 3){
            description1 = "Follow the three-step guide to view what Logix offers to Shippers"
            description2 = "Find container from preferred company with ease"
            description3 = "Communicate with freight forwarders or Shipping container companies regarding shipment of goods"
            description4 = "View map of empty containers near your location and utilize with ease"
        }


        val sliderPage1 = SliderPage()
        sliderPage1.title = "Logix"
        sliderPage1.description = description1
        sliderPage1.imageDrawable = R.drawable.intro1
        sliderPage1.bgColor = R.color.ripple

        val sliderPage2 = SliderPage()
        sliderPage2.title = "Logix"
        sliderPage2.description = description2
        sliderPage2.imageDrawable = R.drawable.intro2
        sliderPage2.bgColor = R.color.ripple

        val sliderPage3 = SliderPage()
        sliderPage3.title = "Logix"
        sliderPage3.description = description3
        sliderPage3.imageDrawable = R.drawable.intro3
        sliderPage3.bgColor = R.color.ripple

        val sliderPage4 = SliderPage()
        sliderPage4.title = "Logix"
        sliderPage4.description = description4
        sliderPage4.imageDrawable = R.drawable.intro4
        sliderPage4.bgColor = R.color.ripple

        addSlide(AppIntroFragment.newInstance(sliderPage1))
        addSlide(AppIntroFragment.newInstance(sliderPage2))
        addSlide(AppIntroFragment.newInstance(sliderPage3))
        addSlide(AppIntroFragment.newInstance(sliderPage4))

        // OPTIONAL METHODS
// Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"))
        setSeparatorColor(Color.parseColor("#2196F3"))
        // Hide Skip/Done button.
        showSkipButton(true)
        //setButtonsEnabled(false);
        setFadeAnimation()
        // Turn vibration on and set intensity.
// NOTE: you will probably need to ask VIBRATE permission in Manifest.
        //setVibrate(true)
        //setVibrateIntensity(30)

    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        callMainActivity()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        callMainActivity()
    }

    override fun onSlideChanged(
        oldFragment: Fragment?,
        newFragment: Fragment?
    ) {
        super.onSlideChanged(oldFragment, newFragment)
        // Do something when the slide changes.
    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("msg", "msg")
        }
        startActivity(intent)
        finish()
    }
}