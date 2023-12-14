package com.noteninja.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.noteninja.activities.RefreshActivity


class SharedPreference {
    // Filename for shared preferences.
    val PREFS_FILENAME = "com.noteninja.prefs"

    // SharedPreferences and Editor instances.
    private var mSharedPreferences: SharedPreferences
    private var mEditor: SharedPreferences.Editor

    // Context for accessing the SharedPreferences.
    var context: Context

    // Constructor to initialize SharedPreferences and Editor.
    constructor(context: Context) {
        this.context = context
        mSharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences.edit()

    }

    // Function to retrieve the stored session ID.
    fun getSessionId(): String {
        return mSharedPreferences.getString("sessionId", "")!!
    }

    // Function to store a session ID.
    fun setSessionId(sessionId: String) {
        mEditor.putString("sessionId", sessionId)
        mEditor.apply()
    }

    // Function to retrieve the stored profile as a JSON string.
    fun getProfile(): String {
        return mSharedPreferences.getString("profile","")!!
    }

    // Function to store the profile as a JSON string.
    fun setProfile(profile: String) {
        mEditor.putString("profile", profile)
        mEditor.apply()
    }
}