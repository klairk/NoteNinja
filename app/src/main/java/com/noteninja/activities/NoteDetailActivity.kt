package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.noteninja.databinding.ActivityNoteDetailBinding
import com.noteninja.network.DeleteModel
import com.noteninja.network.EditNotesModel
import com.noteninja.network.RetrofitHelper
import com.noteninja.network.common_dialog_api
import retrofit2.Call
import retrofit2.Response

class NoteDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityNoteDetailBinding

    // Variable to store note ID.
    var mainid = ""

    // Override onCreate method for the activity lifecycle.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the fields with data passed from the intent.
        binding.titleEditText.setText(intent.getStringExtra("note")!!.toString())
        binding.notesEditText.setText(intent.getStringExtra("title")!!.toString())

        // Set an onClickListener for the back button.
        binding.IVback.setOnClickListener {
            // Start MainActivity when back button is clicked.
            startActivity(Intent(this@NoteDetailActivity, MainActivity::class.java))
            // Finish the current activity.
            finish()
        }

        // Set an onClickListener for the update button.
        binding.updateButton.setOnClickListener {
            // Validate input fields and call editNotes function if validation is successful.
            if (binding.titleEditText.text.toString() == "") {
                Toast.makeText(this,  "Enter Title ", Toast.LENGTH_SHORT).show()
            }else if (binding.notesEditText.text.toString() == "") {
                android.widget.Toast.makeText(this,  "Enter Note", android.widget.Toast.LENGTH_SHORT).show()
            }else {
                editNotes()
            }
        }

        // Retrieve and store the note ID form the intent.
        var id = intent.getStringExtra("id").toString();
            mainid = id

        // Set an onClickListener for the delete button.
        binding.deleteTV.setOnClickListener {
            // Call deleteNotes function when delete button is clicked.
            deleteNotes()
        }
    }

    // Function to edit a note.
    fun editNotes(){
        // Show a progress dialog.
        common_dialog_api.showProgressDialog(this)

        // Prepare data for the edit note request.
        val map: HashMap<String, Any> = HashMap()

        map["tittle"] = binding.notesEditText.text.toString()
        map["note"] = binding.titleEditText.text.toString()
        map["note_id"] = mainid

        // Make a network call to edit the note using Retrofit.
        RetrofitHelper.getClient().edit_note(map).enqueue(object : retrofit2.Callback<EditNotesModel>{
            // Handle the response from the network call.
            override fun onResponse(call: Call<EditNotesModel>, response: Response<EditNotesModel>) {
                // Dismiss the progress dialog.
                common_dialog_api.dismissProgressDialog()
                try {
                    // Check if the response is successful and not null.
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()!!.statusCode  == 200){
                            // Finish the activity if edit is successful.
                            finish()
                        } else {
                            // Show error message.
                            Toast.makeText(this@NoteDetailActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        // Show error message.
                        Toast.makeText(this@NoteDetailActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    // Handle exceptions and show error message.
                    common_dialog_api.dismissProgressDialog()
                    Toast.makeText(this@NoteDetailActivity,  e.message.toString(), Toast.LENGTH_SHORT).show()

                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<EditNotesModel>, t: Throwable) {
                // Dismiss the progress dialog and show error message.
                common_dialog_api.dismissProgressDialog()
                Toast.makeText(this@NoteDetailActivity,  "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to delete a note.
    fun deleteNotes(){

        // Show a progress dialog.
        common_dialog_api.showProgressDialog(this)

        // Prepare data for the delete note request/
        val map: HashMap<String, Any> = HashMap()
        map["note_id"] = mainid

        // Make a network call to delete the note using Retrofit.
        RetrofitHelper.getClient().note_delete(map).enqueue(object : retrofit2.Callback<DeleteModel>{
            // Handle the response from the network call.
            override fun onResponse(call: Call<DeleteModel>, response: Response<DeleteModel>) {
                // Dismiss the progress dialog.
                common_dialog_api.dismissProgressDialog()
                try {
                    // CHeck if the response is successful and not null.
                    if (response.isSuccessful && response.body() != null){
                        if (response.body()!!.statusCode  == 200){
                            // Finish the activity if deletion is successful.
                            finish()
                        } else {
                            // Show error message.
                            Toast.makeText(this@NoteDetailActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        // Show error message.
                        Toast.makeText(this@NoteDetailActivity,  response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    // Handle exceptions and show error message.
                    common_dialog_api.dismissProgressDialog()
                    Toast.makeText(this@NoteDetailActivity,  e.message.toString(), Toast.LENGTH_SHORT).show()

                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<DeleteModel>, t: Throwable) {
                common_dialog_api.dismissProgressDialog()
                Toast.makeText(this@NoteDetailActivity,  "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}