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

/**
 * Interface for accessing and modifying preference data returned from SharedPreferences. It's a
 * wrapper of default android SharedPreferences which will wrap a preference it an interface that
 * can provide the data as flowable.
 */
@ExperimentalCoroutinesApi
interface CoroutineSharedPreferences {

    /**
     * Provides a boolean preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun getBoolean(
        key: String,
        defaultValue: Boolean = DEFAULT_BOOLEAN,
        adapter: Adapter<Boolean> = BooleanAdapter
    ): Preference<Boolean>

    /**
     * Provides an int preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun getInt(
        key: String,
        defaultValue: Int = DEFAULT_INT,
        adapter: Adapter<Int> = IntAdapter
    ): Preference<Int>

    /**
     * Provides a long preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun getLong(
        key: String,
        defaultValue: Long = DEFAULT_LONG,
        adapter: Adapter<Long> = LongAdapter
    ): Preference<Long>

    /**
     * Provides a float preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun getFloat(
        key: String,
        defaultValue: Float = DEFAULT_FLOAT,
        adapter: Adapter<Float> = FloatAdapter
    ): Preference<Float>

    /**
     * Provides a string preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun getString(
        key: String,
        defaultValue: String = DEFAULT_STRING,
        adapter: Adapter<String> = StringAdapter
    ): Preference<String>

    /**
     * Provides a string set preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun getStringSet(
        key: String,
        defaultValue: Set<String> = DEFAULT_STRING_SET,
        adapter: Adapter<Set<String>> = StringSetAdapter
    ): Preference<Set<String>>

    /**
     * Provides an enum preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param enumClass Instances of the class that represent the enum class
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun <T : Enum<T>> getEnum(
        key: String,
        defaultValue: T,
        enumClass: Class<T>,
        adapter: Adapter<T> = DefaultEnumAdapterCache.getDefault(defaultValue, enumClass)
    ): Preference<T>

    /**
     * Provides an object preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param objectClass Instances of the class that represent the object class
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun <T> getObject(
        key: String,
        defaultValue: T,
        objectClass: Class<T>,
        adapter: Adapter<T> = DefaultObjectAdapterCache.getDefault(defaultValue, objectClass)
    ): Preference<T>

    /**
     * Provides an object preference which is mapped with a key in SharedPreferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @param objectType Type of the common superinterface of the object
     * @param adapter The adapter which is used to retrieve the data from the SharedPreference and
     * mapped to their original domain model.
     * @return The instance of the Preference
     */
    fun <T> getObject(
        key: String,
        defaultValue: T,
        objectType: Type,
        adapter: Adapter<T> = DefaultObjectAdapterCache.getDefault(defaultValue, objectType)
    ): Preference<T>

    /**
     * Removes all the data stored in the SharedPreferences.
     */
    fun clear()

    /**
     * The builder class to build a default implementation of the CoroutineSharedPreferences.
     */
    class Builder {
        private var sharedPreferences: SharedPreferences? = null

        /**
         * Saves the instance of SharedPreferences in which the preference will be saved or
         * retrieved.
         * @param sharedPreferences The SharedPreferences.
         */
        fun preferences(sharedPreferences: SharedPreferences): Builder {
            this.sharedPreferences = sharedPreferences
            return this
        }

        /**
         * Builds and returns the default implementation of the CoroutineSharedPreferences.
         * @return implementation of CoroutineSharedPreferences
         * @throws NullPointerException If no implementation of SharedPreferences isn't provided
         * via preferences(#SharedPreferences) it will throw a null pointer exception
         */
        fun build(): CoroutineSharedPreferences {
            return this.sharedPreferences?.let { DefaultCoroutineSharedPreferences(it) }
                ?: throw NullPointerException("sharedPreferences == null")
        }
    }
}
