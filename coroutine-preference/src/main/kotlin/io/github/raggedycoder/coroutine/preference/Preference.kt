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

/**
 * Interface for accessing and modifying preference data stored in Sharedpreferences.
 * It can supply the data directly also as a flowable implementation which will always provide new
 * data whenever the stored data is modified inside the SharedPreferences.
 * @see SharedPreferences
 * @see SharedPreferences.Editor
 */
interface Preference<T> {

    /**
     * Adapters are the gateway to get and set a value to a preference. The purpose of the adapter
     * is values can be stored in SharedPreferences in a different domain object. It will do those
     * mapping to get & set the data.
     */
    interface Adapter<T> {
        /**
         * Retrieves the value from the preferences.
         * @param key The name of the preference to retrieve.
         * @param preferences The preference from where the data will be retrieved.
         * @return The retrieved value in the preference
         */
        fun get(key: String, preferences: SharedPreferences): T

        /**
         * Sets the value in the preferences editor, to be written back once.
         * {@link #commit} or {@link #apply} are called.
         *  @param key The name of the preference to modify.
         *  @param value The new value for the preference.
         *  @param editor The Interface that will be used for modifying values in a
         *  SharedPreferences object.
         */
        fun set(key: String, value: T, editor: SharedPreferences.Editor)
    }

    /**
     * The name of the preference to retrieve or modify in SharedPreferences.
     */
    val key: String

    /**
     * The value that will be provided if the value is not available.
     */
    val defaultValue: T

    /**
     * The stored value that was retrieved from the SharedPreferences.
     */
    val value: T

    /**
     * The flowable instance which will provide a new value whenever the data is modified in
     * SharedPreference
     */
    val valueAsFlow: Flow<T>

    /**
     * Checks whether the SharedPreferences contains a preference.
     */
    val available: Boolean

    /**
     * Sets a new value in the preferences editor,
     * @param value The new value for the preference. Passing null for this argument is
     * equivalent to calling delete().
     */
    fun set(value: T?)

    /**
     * Removes the stored value of the preference from the SharedPreferences
     */
    fun delete()
}
