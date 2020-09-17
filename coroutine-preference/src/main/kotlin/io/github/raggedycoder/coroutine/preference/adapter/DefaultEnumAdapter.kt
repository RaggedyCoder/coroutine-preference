/*
 * Copyright 2020 RaggedyCoder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class DefaultEnumAdapter<T : Enum<T>>(
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
