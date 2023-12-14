package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.gson.Gson
import com.noteninja.base.AppGlobal
import com.noteninja.databinding.ActivityLoginBinding
import com.noteninja.network.AuthModel
import com.noteninja.network.RetrofitHelper
import com.noteninja.network.common_dialog_api.Companion.dismissProgressDialog
import com.noteninja.network.common_dialog_api.Companion.showProgressDialog
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ser an onClickListener for the sign-in button.
        binding.btsignin.setOnClickListener {
            // Validate email and password input fields.
            if (binding.emailET.text.toString() == "") {
                Toast.makeText(this@LoginActivity,  "Enter Email", Toast.LENGTH_SHORT).show()

            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailET.text.toString().trim()).matches()) {
                Toast.makeText(this@LoginActivity,  "Invalid Email", Toast.LENGTH_SHORT).show()

            }

            else if (binding.passwordsET.text.toString() == "") {
                Toast.makeText(this@LoginActivity,  "Enter Password", Toast.LENGTH_SHORT).show()

            }else{
                // Call login function if validation is successful.
                login()
            }
        }

        // Set an onClickListener for the sign-up text.
        binding.signUpSpamTV.setOnClickListener {
            // Start RegisterActivity on click.
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    // Function to handle user login.
    fun login(){

        // Show a progress dialog.
        showProgressDialog(this)

        // Prepare data for the login request.
        val map: HashMap<String, Any> = HashMap()
        map["device_token"]= "device_token"
        map["device_type"] = 1
        map["device_model"] = "ios"
        map["email"] = binding.emailET.text.toString()
        map["password"] = binding.passwordsET.text.toString()

        // Make a network call to login using Retrofit.
        RetrofitHelper.getClient().login(map).enqueue(object : retrofit2.Callback<AuthModel>{
            // Handle the response form the network call.
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                // Dismiss the progress dialog.
                dismissProgressDialog()
                try {
                    // CHeck if the response is successful and not null.
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()!!.statusCode  == 200){
                            // Save login details and token in AppGlobal.
                            AppGlobal.token = response.body()!!.data.token.toString()
                            AppGlobal.profile= response.body()!!.data
                            AppGlobal.prefsManager.setProfile(Gson().toJson(AppGlobal.profile!!))
                            AppGlobal.prefsManager.setSessionId(AppGlobal.token)
                            // Start MainActivity and finish the current activity.
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            // Show error message.
                            Toast.makeText(this@LoginActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        // Show error message.
                        Toast.makeText(this@LoginActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    // Handle exceptions and show error message.
                    dismissProgressDialog()
                    Toast.makeText(this@LoginActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                // Dismiss the progress dialog and show error message.
                dismissProgressDialog()
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}