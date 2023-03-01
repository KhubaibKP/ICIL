package com.example.icil.beans

import android.app.Activity
import android.content.Context

class SharedPrefs(private val context: Context) {

    fun saveName(name: String) {
        putStringPreference(context, KEY_FILENAME, KEY_NAME, name)
    }

    fun getName(): String? {
        return getStringPreference(context, KEY_FILENAME, KEY_NAME)
    }

    fun savePassword(password: String) {
        putStringPreference(context, KEY_FILENAME, KEY_PASSWORD, password)
    }

    fun getPassword(): String? {
        return getStringPreference(context, KEY_FILENAME, KEY_PASSWORD)
    }

    protected fun putStringPreference(
        context: Context,
        prefsName: String?,
        key: String?,
        value: String?
    ) {
        val preferences = context.getSharedPreferences(prefsName, Activity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    protected fun getStringPreference(
        context: Context,
        prefsName: String?,
        key: String?
    ): String? {
        val preferences =
            context.getSharedPreferences(prefsName, Activity.MODE_PRIVATE)
        return preferences.getString(key, "")
    }


    companion object{
        private const val KEY_FILENAME = "file_icil"
        private const val KEY_NAME = "file_name"
        private const val KEY_PASSWORD   = "file_password"
    }
}