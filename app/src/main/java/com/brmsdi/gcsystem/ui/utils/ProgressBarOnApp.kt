package com.brmsdi.gcsystem.ui.utils

import android.view.View

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface ProgressBarOnApp {

    fun postExecution(view: View) {
        view.visibility = View.GONE
    }

    fun showOrHideView(view: View, visibility: Boolean) {
        view.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}