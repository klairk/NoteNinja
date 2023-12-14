package com.noteninja.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.noteninja.databinding.ActivityMainBinding
import com.noteninja.list.NoteListAdapter
import com.noteninja.network.GetNotesData
import com.noteninja.network.GetNotesResponse
import com.noteninja.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    // Variable to hold the adapter for the notes list.
    var notesAdapter : NoteListAdapter?= null

    // List to store notes data.
    var list: ArrayList<GetNotesData> = arrayListOf()

    // Override onCreate method for the activity lifecycle.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set an onClickListener for the floating action button to add a note.
        binding.floatingActionButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }

        // Set an onClickListener for the settings icon.
        binding.ivsetting.setOnClickListener{
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }

        // Set up the RecyclerView with a LinearLayout Manager and the notes adapter.
        binding.mainRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        notesAdapter = NoteListAdapter(this,list)
        binding.mainRV.adapter = notesAdapter

        // Set an onRefreshListener for the SwipeRefreshLayout.
        binding.refresh.setOnRefreshListener {
        getNotesList()
        }
    }

    // Override onResume method for the activity lifecycle.
    override fun onResume() {
        super.onResume()
        // Fetch the list of notes when the activity resumes.
        getNotesList()
    }

    // Function to fetch and update the list of notes.
    fun getNotesList(){
        // Indicate loading in the UI.
        binding.refresh.isRefreshing = true

        // Make a network call to fetch notes using Retrofit.
        RetrofitHelper.getClient().getnotes().enqueue(object  : retrofit2.Callback<GetNotesResponse>{
            // Handle the response from the network call.
            override fun onResponse(call: Call<GetNotesResponse>, response: Response<GetNotesResponse>) {
                // Stop the loading indicator.
                binding.refresh.isRefreshing = false
                try {
                    // Check if the response is successful and not null.
                    if (response.isSuccessful && response.body() != null){
                        // Clear the existing list and add all fetched notes.
                        list.clear()
                        list.addAll(response.body()!!.data)
                        // Reverse the list for display.
                        list.reverse()
                        // Show or hide empty list message based on the notes count.
                        if (list.size == 0){
                            binding.emptylistTV.visibility = View.VISIBLE
                        }else{
                            binding.emptylistTV.visibility = View.GONE
                        }

                        // Notify the adapter about data changes.
                        notesAdapter!!.notifyDataSetChanged()
                    }
                }catch (e:Exception){
                    // Handle exceptions and stop the loading indicator.
                    binding.refresh.isRefreshing = false

                    Toast.makeText(this@MainActivity,  e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            // Handle failure of the network call.
            override fun onFailure(call: Call<GetNotesResponse>, t: Throwable) {
                // Stop the loading indicator and show an error message.
                binding.refresh.isRefreshing = false
                Toast.makeText(this@MainActivity,  "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}