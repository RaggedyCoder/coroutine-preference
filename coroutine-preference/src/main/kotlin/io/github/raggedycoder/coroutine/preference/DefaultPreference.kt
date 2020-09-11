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
