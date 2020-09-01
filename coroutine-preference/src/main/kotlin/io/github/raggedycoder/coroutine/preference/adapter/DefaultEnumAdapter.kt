package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DefaultEnumAdapter<T : Enum<T>>(
    private val enumClass: Class<T>,
    private val defaultValue: T
) : Adapter<T> {
    override fun get(key: String, preferences: SharedPreferences): T {
        val result = preferences.getString(key, null)
        return if (result == null) defaultValue else java.lang.Enum.valueOf(enumClass, result)
    }

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, value.name)
    }

}
