package com.brmsdi.gcsystem.ui.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.brmsdi.gcsystem.data.listeners.ItemDialogListener
import com.brmsdi.gcsystem.data.listeners.dialog.DialogConfirmAndCancelListener
import com.brmsdi.gcsystem.databinding.AddItemBinding

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
            cancelTitle: String,
            listener: DialogConfirmAndCancelListener
        ): AlertDialog {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(confirmTitle) { _, _ -> listener.confirm() }
                .setNegativeButton(cancelTitle) { _, _ -> listener.cancel() }
            return builder.create()
        }

        fun addItemDialog(
            context: Context,
            listener: ItemDialogListener
        ): AlertDialog {
            val dialogBuilder = AlertDialog.Builder(context)
            val layoutInflater = LayoutInflater.from(context)
            val dialogBinding = AddItemBinding.inflate(layoutInflater)
            dialogBuilder.setView(dialogBinding.root)
            dialogBinding.buttonSaveItem.setOnClickListener {
                listener.add(
                    dialogBinding.editDescription.text.toString(),
                    dialogBinding.editItemQuantity.text.toString(),
                    dialogBinding.editItemValue.text.toString()
                )
            }
            dialogBinding.buttonCancelItem.setOnClickListener { listener.cancel() }
            return dialogBuilder.create()
        }

        fun closeDialog(alertDialog: AlertDialog) {
            if (alertDialog.isShowing) alertDialog.dismiss()
        }
    }

}