package com.ap.demo.utils

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import com.ap.demo.R

class Loader {
    companion object {
        fun getLoader(activity: Activity): Dialog {
            val dialog = Dialog(activity, R.style.DialogFragmentTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialog.window!!.setDimAmount(0.2f)
            dialog.setContentView(R.layout.loader_dialog)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            return dialog
        }
    }
}