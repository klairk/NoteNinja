package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.noteninja.databinding.ActivityOtpBinding
import com.noteninja.network.AuthModel
import com.noteninja.network.RetrofitHelper
import com.noteninja.network.common_dialog_api.Companion.dismissProgressDialog
import com.noteninja.network.common_dialog_api.Companion.showProgressDialog
import retrofit2.Call
import retrofit2.Response

class OtpActivity : AppCompatActivity() {

    lateinit var binding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the OTP field with the OTP received from the intent.
        binding.otp.setOTP(intent.getStringExtra("otp")!!.toString())

        // Set an onClickListener for the OTP verification button.
        binding.btotp.setOnClickListener{
            // Validate the OTP field and call verify otp function of validation is successful.
            if (binding.otp.otp!! == "") {
                Toast.makeText(this@OtpActivity, "Enter OTP", Toast.LENGTH_SHORT).show()
            }else {
                verifyotp()
            }
        }
    }

    // Function to verify OTP.
    fun verifyotp() {
        // Show a progress dialog.
        showProgressDialog(this)

        // Prepare data for the OTP verification request.
        val map: HashMap<String, Any> = HashMap()
        map["otp"] =binding.otp.otp!!
        map["email"] = intent.getStringExtra("email").toString()

        // Make a network call to verify OTP using Retrofit.
        RetrofitHelper.getClient().otp(map).enqueue(object : retrofit2.Callback<AuthModel>{
            // Handle the response form the network call.
            override fun onResponse(call: retrofit2.Call<AuthModel>, response: Response<AuthModel>
            ) {
                // Dismiss the progress dialog.
                dismissProgressDialog()
                try {
                    // Check if the response is successful and not null.
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()!!.statusCode  == 200){
                            // Navigate to LoginActivity on successful verification.
                            startActivity(
                                Intent(this@OtpActivity, LoginActivity::class.java)
                            )
                            // Finish all previous activities.
                            finishAffinity()
                        }else{
                            // Show error message.
                            Toast.makeText(this@OtpActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        // Show error message.
                        Toast.makeText(this@OtpActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    // Handle exceptions and show error message.
                    Toast.makeText(this@OtpActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                // Dismiss the progress dialog and show error message.
                dismissProgressDialog()
                Toast.makeText(this@OtpActivity, "${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }

}