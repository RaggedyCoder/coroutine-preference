package io.github.raggedycoder.coroutine.preference

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class DefaultPreference<T>
internal constructor(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    private val adapter: Adapter<T>,
    keyChanges: Flow<String>
) : Preference<T> {

    private val valueChanges =
        keyChanges.filter { it == key }.onStart { emit("<init>") }.conflate().map { get() }

    override fun get(): T =
        if (preferences.contains(key)) adapter.get(key, preferences) else defaultValue

    override fun getAsFlow() = valueChanges

    override fun set(value: T?) {
        val editor = preferences.edit()
        editor.apply {
            if (value != null) adapter.set(key, value, this) else remove(key)
        }
        editor.apply()
    }

    override fun isSet() = preferences.contains(key)

    override fun delete() = set(null)
}
