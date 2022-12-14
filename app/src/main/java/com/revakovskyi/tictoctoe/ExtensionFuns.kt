package com.revakovskyi.tictoctoe

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Activity.showResultsDialog(
    title: String,
    message: String,
    positiveAction: () -> Unit,
    negativeAction: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setIcon(R.drawable.congrats_icon)
        .setMessage(message)
        .setPositiveButton(R.string.start_new_game) { _, _ ->
            positiveAction()
        }
        .setNegativeButton(R.string.exit) { _, _ ->
            negativeAction()
        }
        .create()
        .show()
}

fun Activity.showToast() {
    var toast = Toast(this)
    toast.cancel()
    toast = Toast.makeText(this, getString(R.string.select_free_cell), Toast.LENGTH_SHORT)
    toast.show()
}

fun Activity.showExitDialog() {
    AlertDialog.Builder(this)
        .setTitle(R.string.exit)
        .setIcon(R.drawable.question_icon)
        .setMessage(R.string.want_to_exit)
        .setPositiveButton(R.string.yes) { _, _ -> finish() }
        .setNegativeButton(R.string.no, null)
        .create()
        .show()
}