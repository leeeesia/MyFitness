package ru.myfitness.utils

import android.app.AlertDialog
import android.content.Context

object DialogManager {
    fun showDialog(context: Context, msgId: Int, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle("Внимание")
        builder.setMessage(msgId)
        builder.setPositiveButton("Да") { _, _ ->
            listener.onClick()
            dialog?.dismiss()
        }
        builder.setNegativeButton("Нет") { _, _ ->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }
}