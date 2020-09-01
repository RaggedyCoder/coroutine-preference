package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_STRING_SET
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object StringSetAdapter : Adapter<Set<String>> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getStringSet(key, DEFAULT_STRING_SET) ?: DEFAULT_STRING_SET

    override fun set(key: String, value: Set<String>, editor: SharedPreferences.Editor) {
        editor.putStringSet(key, value)
    }
}
