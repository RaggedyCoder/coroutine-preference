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
import io.github.raggedycoder.coroutine.preference.DefaultValues.DEFAULT_STRING
import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal object StringAdapter : Adapter<String> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getString(key, DEFAULT_STRING) ?: DEFAULT_STRING

    override fun set(key: String, value: String, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}
