package com.logistics.logix.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.logistics.logix.R

abstract class BaseActivity : AppCompatActivity() {


    protected fun setupActionBar(title: String, subtitle: String,
                                 homeAsUpEnabled: Boolean) {
        supportActionBar!!.title = title
        supportActionBar!!.subtitle = subtitle
        supportActionBar!!.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        supportActionBar!!.setDisplayShowHomeEnabled(homeAsUpEnabled)
    }

    @SuppressLint("PrivateResource")
    fun setActionBar(context: Context, actionBar: ActionBar, titleName: String, fontSize: Float) {
        // Set the ActionBar background color
        //actionBar.setBackgroundDrawable(new_item ColorDrawable(Color.parseColor("#FF4500")));

        // Create a TextView programmatically.
        val tv = TextView(context)

        // Create a LayoutParams for TextView
        val lp = RelativeLayout.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
            Toolbar.LayoutParams.WRAP_CONTENT
        ) // Height of TextView

        // Apply the layout parameters to TextView widget
        tv.layoutParams = lp

        // Set text to display in TextView
        // This will set the ActionBar title text
        tv.text = titleName

        // Set the text color of TextView
        // This will change the ActionBar title text color
        tv.setTextColor(Color.WHITE)

        // Center align the ActionBar title
        //tv.setGravity(Gravity.CENTER);

        // Set the serif font for TextView text
        // This will change ActionBar title text font
        val typeface = ResourcesCompat.getFont(context, R.font.helveticaneueltstd_th)
        tv.setTypeface(typeface, Typeface.NORMAL)

        // Underline the ActionBar title text
        //tv.setPaintFlags(tv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        // Set the ActionBar title font size
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize)

        // Display a shadow around ActionBar title text
        /*tv.setShadowLayer(
                1.f, // radius
                2.0f, // dx
                2.0f, // dy
                Color.parseColor("#FF8C00") // shadow color
        );*/
        // Set the ActionBar display option
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.customView = tv
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        val upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material)
        //upArrow!!.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP)
        actionBar.setHomeAsUpIndicator(upArrow)
    }

    fun displayHomeAsUpEnabled(actionBar: ActionBar, isEnable: Boolean){
        actionBar.setDisplayHomeAsUpEnabled(isEnable)
        actionBar.setDisplayShowHomeEnabled(isEnable)
    }

}