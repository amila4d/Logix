package com.logistics.logix.database

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

private const val USER_PREFERENCES_FILE_KEY = "com.logistics.user_preferences"
private const val SETTINGS_PREFERENCES_FILE_KEY = "com.logistics.settings_preferences"
private const val SECURE_PREFS_FILE_KEY = "com.logistics.secure_preferences"

fun provideUserPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(USER_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(SETTINGS_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

private fun provideSecurePreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(SECURE_PREFS_FILE_KEY, Context.MODE_PRIVATE)