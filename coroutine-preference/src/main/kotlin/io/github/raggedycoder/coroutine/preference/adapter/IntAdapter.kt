package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_INT
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object IntAdapter : Adapter<Int> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getInt(key, DEFAULT_INT)

    override fun set(key: String, value: Int, editor: SharedPreferences.Editor) {
        editor.putInt(key, value)
    }
}
