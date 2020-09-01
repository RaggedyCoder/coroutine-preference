package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_STRING
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object StringAdapter : Adapter<String> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getString(key, DEFAULT_STRING) ?: DEFAULT_STRING

    override fun set(key: String, value: String, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}
