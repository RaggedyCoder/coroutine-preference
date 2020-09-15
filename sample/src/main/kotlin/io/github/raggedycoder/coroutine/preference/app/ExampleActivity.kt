package io.github.raggedycoder.coroutine.preference.app

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.raggedycoder.coroutine.preference.CoroutineSharedPreferences
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
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

        barEditTextOne.setText(stringPreferenceOne.value)
        barEditTextTwo.setText(stringPreferenceOne.value)
    }

    override fun onResume() {
        super.onResume()
        jobs += createChecboboxConsumerJob(fooCheckBoxOne).apply { start() }
        jobs += createCheckboxJob(fooCheckBoxOne).apply { start() }
        jobs += createChecboboxConsumerJob(fooCheckBoxTwo).apply { start() }
        jobs += createCheckboxJob(fooCheckBoxTwo).apply { start() }

        jobs += createEditTextConsumerJob(barEditTextOne).apply { start() }
        jobs += createEditTextJob(barEditTextOne).apply { start() }
        jobs += createEditTextConsumerJob(barEditTextTwo).apply { start() }
        jobs += createEditTextJob(barEditTextTwo).apply { start() }

    }

    private fun createChecboboxConsumerJob(checkbox: CheckBox) =
        checkbox.checkedChanges()
            .onEach(booleanPreferenceOne::set)
            .launchIn(CoroutineScope(Dispatchers.IO))

    private fun createCheckboxJob(checkbox: CheckBox) =
        booleanPreferenceOne
            .valueAsFlow
            .filter { !checkbox.isFocused }
            .onEach(checkbox::setChecked)
            .launchIn(CoroutineScope(Dispatchers.Main))

    private fun createEditTextConsumerJob(editText: EditText) =
        editText.textChanges()
            .debounce(500)
            .map { it.toString() }
            .onEach(stringPreferenceOne::set)
            .launchIn(CoroutineScope(Dispatchers.IO))

    private fun createEditTextJob(editText: EditText) =
        stringPreferenceOne
            .valueAsFlow
            .filter { !editText.isFocused }
            .onEach(editText::setText)
            .launchIn(CoroutineScope(Dispatchers.Main))

    override fun onPause() {
        super.onPause()
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}
