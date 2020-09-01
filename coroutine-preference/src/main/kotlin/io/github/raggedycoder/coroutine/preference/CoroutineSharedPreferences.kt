package io.github.raggedycoder.coroutine.preference

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_BOOLEAN
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_FLOAT
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_INT
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_LONG
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_STRING
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_STRING_SET
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import io.github.raggedycoder.coroutine.preference.adapter.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
interface CoroutineSharedPreferences {

    fun getBoolean(
        key: String,
        defaultValue: Boolean = DEFAULT_BOOLEAN,
        adapter: Adapter<Boolean> = BooleanAdapter
    ): Preference<Boolean>

    fun getInt(
        key: String,
        defaultValue: Int = DEFAULT_INT,
        adapter: Adapter<Int> = IntAdapter
    ): Preference<Int>

    fun getLong(
        key: String,
        defaultValue: Long = DEFAULT_LONG,
        adapter: Adapter<Long> = LongAdapter
    ): Preference<Long>

    fun getFloat(
        key: String,
        defaultValue: Float = DEFAULT_FLOAT,
        adapter: Adapter<Float> = FloatAdapter
    ): Preference<Float>

    fun getString(
        key: String,
        defaultValue: String = DEFAULT_STRING,
        adapter: Adapter<String> = StringAdapter
    ): Preference<String>

    fun getStringSet(
        key: String,
        defaultValue: Set<String> = DEFAULT_STRING_SET,
        adapter: Adapter<Set<String>> = StringSetAdapter
    ): Preference<Set<String>>

    fun <T : Enum<T>> getEnum(
        key: String,
        defaultValue: T,
        enumClass: Class<T>,
        adapter: Adapter<T> = DefaultEnumAdapterCache.getDefault(defaultValue, enumClass)
    ): Preference<T>

    fun <T> getObject(
        key: String,
        defaultValue: T,
        objectClass: Class<T>,
        adapter: Adapter<T> = DefaultObjectAdapterCache.getDefault(defaultValue, objectClass)
    ): Preference<T>

    fun <T> getObject(
        key: String,
        defaultValue: T,
        objectType: Type,
        adapter: Adapter<T> = DefaultObjectAdapterCache.getDefault(defaultValue, objectType)
    ): Preference<T>

    fun clear()

    class Builder {
        private var sharedPreferences: SharedPreferences? = null

        fun preferences(sharedPreferences: SharedPreferences): Builder {
            this.sharedPreferences = sharedPreferences
            return this
        }

        fun build(): CoroutineSharedPreferences {
            val sharedPreferences =
                this.sharedPreferences ?: throw NullPointerException("sharedPreferences == null")
            return DefaultCoroutineSharedPreferences(sharedPreferences)
        }
    }
}
