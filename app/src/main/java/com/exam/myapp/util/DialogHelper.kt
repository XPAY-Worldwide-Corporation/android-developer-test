package com.exam.myapp.util

import android.app.Activity
import android.app.ProgressDialog

object DialogHelper {

    private var progressDialog: ProgressDialog? = null

    fun showLoading(activity: Activity?, title: String?, message: String): ProgressDialog {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(activity, title, message)
            progressDialog!!.setCancelable(false)

            if (progressDialog != null) {
                progressDialog!!.show()
            }
        }
        return progressDialog as ProgressDialog
    }

    fun dismissProgressDialog(): Boolean {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        progressDialog = null

        return true
    }
}