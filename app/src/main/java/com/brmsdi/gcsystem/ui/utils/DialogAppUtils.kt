package com.brmsdi.gcsystem.ui.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.brmsdi.gcsystem.data.listeners.dialog.DialogConfirmAndCancelListener

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class DialogAppUtils private constructor() {
    companion object {
        fun createDialog(
            context: Context,
            message: String,
            title: String,
            confirmTitle: String,
            cancelTile: String,
            listener: DialogConfirmAndCancelListener
        ): AlertDialog {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(confirmTitle) { _, _ -> listener.confirm() }
                .setNegativeButton(cancelTile) { _, _ -> listener.cancel() }
            return builder.create()
        }
    }
}