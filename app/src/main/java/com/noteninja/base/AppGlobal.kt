package com.noteninja.base

import android.app.Application
import com.google.gson.Gson
import com.noteninja.network.Profile

class AppGlobal : Application() {

    companion object {
        // Static instance for shared preferences.
        lateinit var prefsManager: SharedPreference
        // Global variable to store the authentication token.
        var token: String = ""
        // Global variable to store the user's profile.
        var profile : Profile? = null
    }

    // Override the onCreate method, which is called when the app is starting.
    override fun onCreate() {
        super.onCreate()
        // Initialize the shared preferences manager.
        prefsManager = SharedPreference(this)
        // Deserialize the stored profile JSON into a profile object using GSON.
        profile = Gson().fromJson(prefsManager.getProfile(), Profile::class.java)
        // Retrieve the stored session ID from shared preferences.
        token = prefsManager.getSessionId()
    }
}