package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_LONG
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object LongAdapter : Adapter<Long> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getLong(key, DEFAULT_LONG)

    override fun set(key: String, value: Long, editor: SharedPreferences.Editor) {
        editor.putLong(key, value)
    }
}
