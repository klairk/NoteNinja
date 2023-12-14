package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.noteninja.databinding.ActivityRegisterBinding
import com.noteninja.network.AuthModel
import com.noteninja.network.RetrofitHelper
import com.noteninja.network.common_dialog_api.Companion.dismissProgressDialog
import com.noteninja.network.common_dialog_api.Companion.showProgressDialog
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set an onClickListener for the sign in button.
        binding.btsignin.setOnClickListener {
            // Validate input fields.
            if (binding.firstNameET.text.toString() == "") {
                Toast.makeText(this,  "Enter First Name", Toast.LENGTH_SHORT).show()
            }else if (binding.lastNameET.text.toString() == "") {
                android.widget.Toast.makeText(this,  "Enter Last Name", android.widget.Toast.LENGTH_SHORT).show()
            }
            else if (binding.emailET.text.toString() == "") {
                Toast.makeText(this,  "Enter Email", Toast.LENGTH_SHORT).show()
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailET.text.toString().trim()).matches()) {
                Toast.makeText(this,  "Invalid Email", Toast.LENGTH_SHORT).show()
            }
            else if (binding.passwordsET.text.toString() == "") {
                Toast.makeText(this,  "Enter Password", Toast.LENGTH_SHORT).show()
            }
            else  if (binding.passwordscET.text.toString() == "") {
                Toast.makeText(this,  "Confirm Password", Toast.LENGTH_SHORT).show()
            }
            else if (binding.passwordsET.text.toString() != (binding.passwordscET.text.toString())){
                Toast.makeText(this,  "Enter Both Password Should be same", Toast.LENGTH_SHORT).show()
            }
            else{
                // Call register function if validation is successful.
                register()
            }
        }
    }

    // Function to handle user registration.
    fun register(){

        // Show a progress dialog.
        showProgressDialog(this)

        // Prepare data for the registration request.
        val map: HashMap<String, Any> = HashMap()
        map["first_name"] = binding.firstNameET.text.toString()
        map["last_name"] = binding.lastNameET.text.toString()
        map["email"] = binding.emailET.text.toString()
        map["password"] = binding.passwordsET.text.toString()
        map["device_token"] = "device_token"
        map["device_type"] =  1
        map["device_model"] =  "ios"

        // Make a network call to register the user using Retrofit.
        RetrofitHelper.getClient().register(map).enqueue(object : retrofit2.Callback<AuthModel>{
            // Handle the response from the network call.
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                // Dismiss the progress dialog.
                dismissProgressDialog()
                try {
                    // Check if the response is successful not null.
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()!!.statusCode  == 200){
                            // Navigate to the OTP activity with OTP and email on successful registration.
                            startActivity(
                                Intent(this@RegisterActivity, OtpActivity::class.java)
                                    .putExtra("otp", response.body()!!.data.otp.toString())
                                    .putExtra("email", binding.emailET.text.toString()))
                        } else {
                            // Show error message.
                            Toast.makeText(
                                this@RegisterActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else {
                        // show error message.
                        Toast.makeText(this@RegisterActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    // Handle exceptions and show error message.
                    dismissProgressDialog()
                    Toast.makeText(this@RegisterActivity,  e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                // Dismiss the progress dialog and show error message.
                dismissProgressDialog()
                Toast.makeText(this@RegisterActivity,  "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}