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
