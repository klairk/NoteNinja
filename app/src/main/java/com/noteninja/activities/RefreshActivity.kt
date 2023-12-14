package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.noteninja.base.AppGlobal
import com.noteninja.databinding.ActivitySplashBinding

class RefreshActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handler to delay the transition from the splash screen.
        Handler(Looper.myLooper()!!).postDelayed({
            // Check if the user is logged in or not using a token.
            if(AppGlobal.token == ""){
                // If not logged in, navigate to LoginActivity.
                startActivity(Intent(this@RefreshActivity, LoginActivity::class.java))
            }
            else
                // If logged in, navigate to MainActivity.
                startActivity(Intent(this@RefreshActivity, MainActivity::class.java))
            // Finish the current activity to remove it from the back stack.
            finish()
        }, 2000) // Delay of 2 seconds.
    }
}