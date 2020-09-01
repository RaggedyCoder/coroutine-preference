package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_FLOAT
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object FloatAdapter : Adapter<Float> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getFloat(key, DEFAULT_FLOAT)

    override fun set(key: String, value: Float, editor: SharedPreferences.Editor) {
        editor.putFloat(key, value)
    }
}
