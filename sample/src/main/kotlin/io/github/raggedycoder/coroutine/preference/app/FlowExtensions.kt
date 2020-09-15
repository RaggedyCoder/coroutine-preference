package io.github.raggedycoder.coroutine.preference.app


import android.text.Editable
import android.text.TextWatcher
import android.widget.CompoundButton
import android.widget.TextView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive

@ExperimentalCoroutinesApi
fun CompoundButton.checkedChanges() = callbackFlow {
    setOnCheckedChangeListener { _, isChecked -> if (isActive) offer(isChecked) }
    awaitClose { setOnCheckedChangeListener(null) }
}

@ExperimentalCoroutinesApi
fun TextView.textChanges() = callbackFlow<CharSequence?> {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isActive) offer(s)
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}
