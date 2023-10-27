package com.dev.frequenc.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.dev.frequenc.R


fun EditText.textChanged(liveData: MutableLiveData<String>) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // Leaving it blank for now
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Leaving it blank for now
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            liveData.value = s.toString()
        }
    })
}
fun View.showAlertDialog(title: String, message: String, positiveButtonText: String, negativeButtonText: String,
                         positiveButtonClick: () -> Unit, negativeButtonClick: () -> Unit) {
    val builder = AlertDialog.Builder(context ,  com.google.android.material.R.style.AlertDialog_AppCompat)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(positiveButtonText) { _, _ -> positiveButtonClick() }
    builder.setNegativeButton(negativeButtonText) { _, _ -> negativeButtonClick() }
    val dialog = builder.create()
    dialog.show()
}