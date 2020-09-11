package io.github.raggedycoder.coroutine.preference

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
class DefaultCoroutineSharedPreferences
internal constructor(
    private val sharedPreferences: SharedPreferences
) : CoroutineSharedPreferences {
    private val keyChanges = callbackFlow {
        val changeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key: String ->
            offer(key)
        }
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListener)
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(changeListener)
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean,
        adapter: Adapter<Boolean>
    ): Preference<Boolean> =
        DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun getInt(key: String, defaultValue: Int, adapter: Adapter<Int>): Preference<Int> =
        DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun getLong(
        key: String,
        defaultValue: Long,
        adapter: Adapter<Long>
    ): Preference<Long> =
        DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun getFloat(
        key: String,
        defaultValue: Float,
        adapter: Adapter<Float>
    ): Preference<Float> =
        DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun getString(
        key: String,
        defaultValue: String,
        adapter: Adapter<String>
    ): Preference<String> =
        DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun getStringSet(
        key: String,
        defaultValue: Set<String>,
        adapter: Adapter<Set<String>>
    ): Preference<Set<String>> =
        DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun <T : Enum<T>> getEnum(
        key: String,
        defaultValue: T,
        enumClass: Class<T>,
        adapter: Adapter<T>
    ): Preference<T> = DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun <T> getObject(
        key: String,
        defaultValue: T,
        objectClass: Class<T>,
        adapter: Adapter<T>
    ): Preference<T> = getObject(key, defaultValue, objectClass as Type, adapter)

    override fun <T> getObject(
        key: String,
        defaultValue: T,
        objectType: Type,
        adapter: Adapter<T>
    ): Preference<T> = DefaultPreference(sharedPreferences, key, defaultValue, adapter, keyChanges)

    override fun clear() = sharedPreferences.edit().clear().apply()
}
