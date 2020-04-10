package com.logistics.logix.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.util.*

class AppHelper  {

    companion object {
        fun isInternetAvailable(context: Context): Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }

        fun convertDateToFormattedDate(mDate: String): String? {
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                fromFormat.isLenient = false
                val toFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH)
                val date = fromFormat.parse(mDate)
                return toFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun formatDate(dateAndTme: String): String {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
            val result: Date
            result = df.parse(dateAndTme)
            val sdf = SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US)
            return sdf.format(result)
        }

        fun getCurrentDate(dateAndTme: String): String {
            val df = SimpleDateFormat("yyyy-M-d", Locale.US)
            val result: Date
            result = df.parse(dateAndTme)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            return sdf.format(result)
        }

        fun getCalendarObject(value : String) : Calendar{
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            cal.time = sdf.parse(value)
            return cal
        }

        fun convertDateTimeToRequiredType(date: String): String {
            val fromFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val toFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val convertedDate = fromFormat.parse(date)
            return toFormat.format(convertedDate)
        }

        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getDateTimeSumOrAdd(noOfDay: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, noOfDay)
            return calendar.time
        }

        fun getMonthSumOrAdd(noOfMonths: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, noOfMonths)
            return calendar.time
        }

        private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        fun removeLast(text: String, n: Int): String {
            var resultString = ""
            if (text != null && text.isNotEmpty()) {
                resultString = text.substring(0, text.length - n)
            }
            return resultString
        }

          @SuppressLint("HardwareIds")
          fun getAndroidId(context: Context): String? {
            return Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ANDROID_ID)
        }

        fun toSimpleString(date: Date) : String {
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(date)
        }
    }

}
