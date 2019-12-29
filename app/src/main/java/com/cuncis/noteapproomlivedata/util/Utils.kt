package com.cuncis.noteapproomlivedata.util

import android.content.Context
import android.util.Log
import android.widget.Toast

class Utils {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show()
        }

        fun logd(message: String) {
            Log.d("_logNote", "" + message)
        }
    }
}