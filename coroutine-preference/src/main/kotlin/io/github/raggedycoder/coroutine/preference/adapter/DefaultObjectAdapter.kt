package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
class DefaultObjectAdapter<T>(
    private val type: Type,
    private val defaultValue: T,
    private val gson: Gson = gsonConverter
) : Adapter<T> {

    override fun get(key: String, preferences: SharedPreferences): T {
        val result = preferences.getString(key, EMPTY_GSON)
        return if (result == null || result == EMPTY_GSON) defaultValue
        else gson.fromJson(result, type)
    }

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, gson.toJson(value))
    }

    companion object {
        private val gsonConverter = GsonBuilder().create()
        private const val EMPTY_GSON = "{}"
    }
}
