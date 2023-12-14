package com.noteninja.network

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.noteninja.R

// Class to manage common dialog functionalities.
class common_dialog_api {

    companion object{
        // Variable to hold the dialog instance.
        var dialog: Dialog? = null

        // Function to show a progress dialog.
        fun showProgressDialog(context: Context) {
            // Initialize the dialog with the given context.
            dialog = Dialog(context)
            // Request no title for the dialog.
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            // Set the background of the dialog window to be transparent.
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // Make the dialog non-cancelable.
            dialog!!.setCancelable(false)
            // Set the layout of the dialog to a custom progress bar layout.
            dialog!!.setContentView(R.layout.dialog_prograss_bar)
            // Show the dialog.
            dialog!!.show()
        }

        // Function to dismiss the progress dialog.
        fun dismissProgressDialog() {
            // Check if the dialog is not null and then dismiss it.
            if (dialog != null) {
                dialog!!.dismiss()
                // Set the dialog instance to null.
                dialog = null
            }
        }
    }
}