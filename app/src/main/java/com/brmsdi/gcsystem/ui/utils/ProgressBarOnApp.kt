package com.brmsdi.gcsystem.ui.utils

import android.view.View
import android.widget.ProgressBar


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ProgressBarOnApp {

    fun onProgress(view: View) {
        view.visibility = View.VISIBLE
    }

    fun postExecution(view: View) {
        view.visibility = View.GONE
    }

    fun showOrHideView(view: View, visibility: Boolean) {
        view.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}