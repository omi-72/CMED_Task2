package com.example.cmed_task2

import android.app.Dialog
import android.content.Context

class ProgressDialog (private val context: Context) {

    var dialog: Dialog = Dialog(context)

    fun progressDialog(): Dialog {
        dialog.setContentView(R.layout.dialog_progress)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setCancelable(false)
        return dialog
    }
}