package io.github.raggedycoder.coroutine.preference

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow

interface Preference<T> {
    interface Adapter<T> {
        fun get(key: String, preferences: SharedPreferences): T
        fun set(key: String, value: T, editor: SharedPreferences.Editor)
    }

    val key: String

    val defaultValue: T

    val value: T

    val valueAsFlow: Flow<T>

    val available: Boolean

    fun set(value: T?)

    fun delete()
}
