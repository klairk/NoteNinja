package com.noteninja.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noteninja.R
import com.noteninja.base.AppGlobal
import com.noteninja.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the name and email fields form the global user profile.
        binding.nametv.text = AppGlobal.profile?.first_name+" "+ AppGlobal.profile?.last_name
        binding.emailtv.text =AppGlobal.profile?.email

        // Set an onClickListener for the back button.
        binding.backiv.setOnClickListener {
            // Navigate back to MainActivity.
            startActivity(Intent(this@SettingActivity, MainActivity::class.java))
            finish()
        }

        // Ser an onClickListener for the change password layout.
        binding.llchange.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        // Set an onClickListener for the logout layout.
        binding.lllogout.setOnClickListener {
            // Show the logout confirmation dialog.
            showAlertDialog()
        }
    }

    // Function to show logout confirmation dialog.
    private fun showAlertDialog(){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Logout")
        alertDialog.setMessage("Are you sure you want to Logout?")
        alertDialog.setPositiveButton(
            "yes"
        ) { _, _ ->
            // Perform logout operation on positive button click.
            logout()
        }
        alertDialog.setNegativeButton(
            "No"
        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    // Function to handle user logout.
    fun logout() {
        // Clear global user data and preferences.
        AppGlobal.token = ""
        AppGlobal.profile = null
        AppGlobal.prefsManager.setSessionId("")
        AppGlobal.prefsManager.setProfile("")
        // Navigate to LoginActivity and clear all previous activities from the stack.
        startActivity(Intent(this@SettingActivity, LoginActivity::class.java))
        finishAffinity();

    }
}