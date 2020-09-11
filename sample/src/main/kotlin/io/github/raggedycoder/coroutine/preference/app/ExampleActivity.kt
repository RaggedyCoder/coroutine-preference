package io.github.raggedycoder.coroutine.preference.app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import io.github.raggedycoder.coroutine.preference.CoroutineSharedPreferences
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExampleActivity : AppCompatActivity() {

    private val sharedPreferences by lazy {
        getSharedPreferences("my-shared-preferences", MODE_PRIVATE)
    }
    private val coroutinePreferences by lazy {
        CoroutineSharedPreferences.Builder()
            .preferences(sharedPreferences)
            .build()
    }

    private val booleanPreferenceOne by lazy {
        coroutinePreferences.getBoolean("foobar_checkbox_1")
    }

    private val booleanPreferenceTwo by lazy {
        coroutinePreferences.getBoolean("foobar_checkbox_2")
    }

    private val stringPreferenceOne by lazy {
        coroutinePreferences.getString("foobar_edittext_1")
    }

    private val stringPreferenceTwo by lazy {
        coroutinePreferences.getString("foobar_edittext_2")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        fooCheckBoxOne.setOnCheckedChangeListener { buttonView, isChecked ->
            booleanPreferenceTwo.set(isChecked)
        }

        fooCheckBoxTwo.setOnCheckedChangeListener { buttonView, isChecked ->
            booleanPreferenceOne.set(isChecked)
        }

        barEditTextOne.addTextChangedListener(barEditTextOneTextChangeListener)

        barEditTextTwo.addTextChangedListener(barEditTextTwoTextChangeListener)

        booleanPreferenceTwo.valueAsFlow.onEach {
            runOnUiThread {
                fooCheckBoxTwo.isChecked = it
            }
        }.launchIn(CoroutineScope(Dispatchers.IO)).start()

        booleanPreferenceOne.valueAsFlow.onEach {
            runOnUiThread {
                fooCheckBoxOne.isChecked = it
            }
        }.launchIn(CoroutineScope(Dispatchers.IO)).start()


        stringPreferenceTwo.valueAsFlow.onEach {
            runOnUiThread {
                barEditTextOne.removeTextChangedListener(barEditTextOneTextChangeListener)
                barEditTextTwo.removeTextChangedListener(barEditTextTwoTextChangeListener)
                barEditTextTwo.setText(it)
                barEditTextOne.addTextChangedListener(barEditTextOneTextChangeListener)
                barEditTextTwo.addTextChangedListener(barEditTextTwoTextChangeListener)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO)).start()

        stringPreferenceOne.valueAsFlow.onEach {
            runOnUiThread {
                barEditTextOne.removeTextChangedListener(barEditTextOneTextChangeListener)
                barEditTextTwo.removeTextChangedListener(barEditTextTwoTextChangeListener)
                barEditTextOne.setText(it)
                barEditTextOne.addTextChangedListener(barEditTextOneTextChangeListener)
                barEditTextTwo.addTextChangedListener(barEditTextTwoTextChangeListener)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO)).start()
    }

    val barEditTextTwoTextChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { stringPreferenceOne.set(it.toString()) }
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    val barEditTextOneTextChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { stringPreferenceTwo.set(it.toString()) }
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
}
