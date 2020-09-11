package io.github.raggedycoder.coroutine.preference

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class DefaultPreference<T>
internal constructor(
    private val preferences: SharedPreferences,
    override val key: String,
    override val defaultValue: T,
    private val adapter: Adapter<T>,
    keyChanges: Flow<String>
) : Preference<T> {

    override val value: T
        get() = if (preferences.contains(key)) adapter.get(key, preferences) else defaultValue

    override val valueAsFlow =
        keyChanges.filter { it == key }.onStart { emit("<init>") }.conflate().map { value }

    override val available: Boolean
        get() = preferences.contains(key)

    override fun set(value: T?) {
        preferences.edit().apply {
            if (value != null) adapter.set(key, value, this) else remove(key)
            apply()
        }
        preferences.contains(key)
    }

    override fun delete() = set(null)
}
