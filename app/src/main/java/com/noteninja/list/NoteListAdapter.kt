package com.noteninja.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.noteninja.activities.NoteDetailActivity
import com.noteninja.databinding.NoteListResourceFileBinding
import com.noteninja.network.GetNotesData

// Adapter class for the RecyclerView displaying notes.
class NoteListAdapter (var context: Context, var list: ArrayList<GetNotesData>):
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){

    // Inner class defining the ViewHolder.
    inner class ViewHolder(val binding: NoteListResourceFileBinding): RecyclerView.ViewHolder(binding.root){

        init {
            // Setting a click listener on each item in the RecyclerView.
            binding.root.setOnClickListener {
                // Creating an intent to start NoteDetailActivity with note details.
                val intent = Intent(context, NoteDetailActivity::class.java)
                    .putExtra("id", list[adapterPosition].id.toString())
                    .putExtra("title", list[adapterPosition].tittle)
                    .putExtra("note", list[adapterPosition].note)
                context.startActivity(intent)

            }
        }
    }

    // Function to create new views.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the custom layout.
        return ViewHolder(NoteListResourceFileBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    // Function to replace the contents of a view.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from the dataset at this position and replace the contents of the view with that element.
        holder.binding.notesTV.text = list[position].tittle.toString()
        holder.binding.titleTV.text = list[position].note.toString()
    }

    // Function to return the size of the dataset.
    override fun getItemCount(): Int {
        return list.size
    }
}