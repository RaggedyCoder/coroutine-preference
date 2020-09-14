package io.github.raggedycoder.coroutine.preference.app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.raggedycoder.coroutine.preference.CoroutineSharedPreferences
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

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

    private val stringPreferenceOne by lazy {
        coroutinePreferences.getString("foobar_edittext_1")
    }

    private val jobs = mutableListOf<Job>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        fooCheckBoxOne.isChecked = booleanPreferenceOne.value
        fooCheckBoxTwo.isChecked = booleanPreferenceOne.value

        fooCheckBoxOne.setOnCheckedChangeListener { _, isChecked ->
            booleanPreferenceOne.set(isChecked)
        }

        fooCheckBoxTwo.setOnCheckedChangeListener { _, isChecked ->
            booleanPreferenceOne.set(isChecked)
        }

        barEditTextOne.setText(stringPreferenceOne.value)
        barEditTextTwo.setText(stringPreferenceOne.value)

        barEditTextOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { stringPreferenceOne.set(it.toString()) }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        barEditTextTwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { stringPreferenceOne.set(it.toString()) }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        jobs += createCheckboxJob(fooCheckBoxOne).apply { start() }
        jobs += createCheckboxJob(fooCheckBoxTwo).apply { start() }

        jobs += createEditTextJob(barEditTextOne).apply { start() }
        jobs += createEditTextJob(barEditTextTwo).apply { start() }

    }

    private fun createCheckboxJob(checkbox: CheckBox) =
        booleanPreferenceOne
            .valueAsFlow
            .drop(1)
            .filter { !checkbox.isFocused }
            .onEach(checkbox::setChecked)
            .launchIn(CoroutineScope(Dispatchers.Main))

    private fun createEditTextJob(editText: EditText) =
        stringPreferenceOne
            .valueAsFlow
            .drop(1)
            .filter { !editText.isFocused }
            .debounce(500)
            .onEach(editText::setText)
            .launchIn(CoroutineScope(Dispatchers.Main))

    override fun onPause() {
        super.onPause()
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}
