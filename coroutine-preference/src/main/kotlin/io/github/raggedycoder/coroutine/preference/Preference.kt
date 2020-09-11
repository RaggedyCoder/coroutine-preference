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
