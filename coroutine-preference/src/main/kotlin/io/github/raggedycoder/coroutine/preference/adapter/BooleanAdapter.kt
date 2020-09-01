package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_BOOLEAN
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object BooleanAdapter : Adapter<Boolean> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getBoolean(key, DEFAULT_BOOLEAN)

    override fun set(key: String, value: Boolean, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}
