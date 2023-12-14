package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.noteninja.databinding.ActivityChangePasswordBinding
import com.noteninja.network.AuthModel
import com.noteninja.network.RetrofitHelper
import com.noteninja.network.common_dialog_api.Companion.dismissProgressDialog
import com.noteninja.network.common_dialog_api.Companion.showProgressDialog
import retrofit2.Call
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var binding : ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set a click listener on the back button.
        binding.backIv.setOnClickListener {
            // Start the SettingActivity when the back button is clicked.
            startActivity(Intent(this@ChangePasswordActivity, SettingActivity::class.java))
            // Finish the current activity.
            finish()
        }

        // Set a click listener on the change button.
        binding.btchange.setOnClickListener {

            // Validate input fields and show toast messages for errors.
            if (binding.oldpasswordsET.text.toString() == "") {
                Toast.makeText(this,  "Enter Old Password", Toast.LENGTH_SHORT).show()

            }

           else if (binding.passwordsET.text.toString() == "") {
                Toast.makeText(this,  "Please Enter Password", Toast.LENGTH_SHORT).show()

            }

            else if (binding.passwordscET.text.toString() == "") {
                Toast.makeText(this,  "Please Confirm Password", Toast.LENGTH_SHORT).show()

            }

            else if (binding.passwordsET.text.toString() != (binding.passwordscET.text.toString())){
                Toast.makeText(this,  "Enter Both Password Should be same", Toast.LENGTH_SHORT).show()

            }else{
                // Call changePassword function if validation passes.
                changePassword()
            }
        }
    }

    // Function to handle password change.
    fun changePassword() {

        // Show a progress dialog.
        showProgressDialog(this)

        // Prepare data for the password change request.
        val map: HashMap<String, Any> = HashMap()

        // Prepare data for the password change request.
        map["old_password"] = binding.oldpasswordsET.text.toString()
        map["new_password"] = binding.passwordsET.text.toString()

        // Make a network call to change the password using Retrofit.
        RetrofitHelper.getClient().changePassword(map).enqueue(object : retrofit2.Callback<AuthModel>{
            // Handle the response from the network call.
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                dismissProgressDialog()
                try {
                    // Check if the response is successful and not null.
                    if (response.isSuccessful && response.body() !=null){
                        // Check the status code in the respnse.
                        if (response.body()!!.statusCode  == 200){
                            // Show success message.
                            Toast.makeText(this@ChangePasswordActivity,  "Password Changed Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            // Show error message.
                            Toast.makeText(this@ChangePasswordActivity,    response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else  {
                        // Show error message.
                        Toast.makeText(this@ChangePasswordActivity,    response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    // Handle exceptions and show error message.
                    dismissProgressDialog()
                    Toast.makeText(this@ChangePasswordActivity,    e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                // Dismiss the progress Dialog and show error message.
                dismissProgressDialog()
                Toast.makeText(this@ChangePasswordActivity,   "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}