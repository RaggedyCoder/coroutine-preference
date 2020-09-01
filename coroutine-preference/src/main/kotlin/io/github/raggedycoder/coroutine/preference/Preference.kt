package io.github.raggedycoder.coroutine.preference

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow

interface Preference<T> {
    interface Adapter<T> {
        fun get(key: String, preferences: SharedPreferences): T
        fun set(key: String, value: T, editor: SharedPreferences.Editor)
    }

    fun get(): T

    fun getAsFlow(): Flow<T>

    fun set(value: T?)

    fun isSet(): Boolean

    fun delete()
}
