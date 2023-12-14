package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.noteninja.databinding.ActivityAddNoteBinding
import com.noteninja.network.AddNotesModel
import com.noteninja.network.RetrofitHelper
import com.noteninja.network.common_dialog_api
import retrofit2.Call
import retrofit2.Response

class AddNoteActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set an onClickListener on the back button.
        binding.backIV.setOnClickListener {
            // Start MainActivity when back button is clicked.
            startActivity(Intent(this@AddNoteActivity, MainActivity::class.java))
            // Finish the current activity.
            finish()
        }

        // Set an onClickListener on the save button.
        binding.saveButton.setOnClickListener {
            // Check if title or note fields are empty and show a Toast message if they are.
            if (binding.titleEditText.text.toString() == "") {
                Toast.makeText(this,  "Enter Title ", Toast.LENGTH_SHORT).show()
            }else if (binding.notesEditText.text.toString() == "") {
                android.widget.Toast.makeText(this,  "Enter Note", android.widget.Toast.LENGTH_SHORT).show()
            }else {
                // If both fields are filled, call addNotes function.
                addNotes()
            }
        }
    }

    // Function to add notes.
    fun addNotes(){
        // Show a progress dialog.
        common_dialog_api.showProgressDialog(this)

        // Create a HashMap to hold note data.
        val map: HashMap<String, Any> = HashMap()

        // Populate the HashMap with title and note data.
        map["tittle"] = binding.notesEditText.text.toString()
        map["note"] = binding.titleEditText.text.toString()

        // Make a network call to add a note using Retrofit.
        RetrofitHelper.getClient().add_note(map).enqueue(object : retrofit2.Callback<AddNotesModel>{
            // Handle the response from the network call.
            override fun onResponse(call: Call<AddNotesModel>, response: Response<AddNotesModel>) {
                // Dismiss the progress dialog.
                common_dialog_api.dismissProgressDialog()
                try {
                    // Check if the response is successful and not null.
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()!!.statusCode  == 200){
                            // Finish the activity if note is successfully added.
                            finish()
                        } else {
                            // Show a Toast with error message.
                            Toast.makeText(this@AddNoteActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        // Show a Toast with error message.
                        Toast.makeText(this@AddNoteActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()

                    }
                }catch (e: Exception){
                    // Dismiss the progress dialog and show an error message in case of an exception.
                    common_dialog_api.dismissProgressDialog()
                    Toast.makeText(this@AddNoteActivity,  e.message.toString(), Toast.LENGTH_SHORT).show()

                }
            }
            // Handle failure of the network call.
            override fun onFailure(call: Call<AddNotesModel>, t: Throwable) {
                // Dismiss the progress dialog and show an error message.
                common_dialog_api.dismissProgressDialog()
                Toast.makeText(this@AddNoteActivity,  "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}