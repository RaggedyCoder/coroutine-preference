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
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultPreferenceTest {

    private val preferences: SharedPreferences = mock()
    private val key: String = "default_key"
    private val defaultValue: String = "default"
    private val adapter: Preference.Adapter<String> = mock()
    private val keyChanges: Flow<String> = mock()

    private val defaultPreference = DefaultPreference(
        preferences,
        key,
        defaultValue,
        adapter,
        keyChanges
    )

    @Test
    fun testKey() {
        key assertEquals defaultPreference.key
    }

    @Test
    fun testGetWhenPreferenceHasKey() {
        val expectedResult = "result"
        whenever(preferences.contains(key)).thenReturn(true)
        whenever(adapter.get(key, preferences)).thenReturn(expectedResult)

        val actualResult = defaultPreference.value
        expectedResult assertEquals actualResult
        preferences verify { contains(key) }
        adapter verify { get(key, preferences) }
    }

    @Test
    fun testGetWhenPreferenceDoesNotHasKey() {
        whenever(preferences.contains(key)).thenReturn(false)

        val actualResult = defaultPreference.value
        defaultValue assertEquals actualResult
        preferences verify { contains(key) }
        adapter never { get(key, preferences) }
    }

    @Test
    fun testIsSet() {
        val expectedResult = true
        whenever(preferences.contains(key)).thenReturn(expectedResult)

        val actualResult = defaultPreference.available
        expectedResult assertEquals actualResult
        preferences verify { contains(key) }
    }

    @Test
    fun testSetForNonNullValue() {
        val mockValue = "mockValue"
        val mockEditor: SharedPreferences.Editor = mock()
        whenever(preferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.remove(key)).thenReturn(mockEditor)

        defaultPreference.set(mockValue)

        mockEditor never { remove(key) }
        mockEditor verify { apply() }
        adapter verify { set(key, mockValue, mockEditor) }
    }

    @Test
    fun testSetForNullValue() {
        val mockValue: String? = null
        val mockEditor: SharedPreferences.Editor = mock()
        whenever(preferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.remove(key)).thenReturn(mockEditor)

        defaultPreference.set(mockValue)

        mockEditor verify { remove(key) }
        mockEditor verify { apply() }
        adapter never { set(key, "mockValue", mockEditor) }
    }

    @Test
    fun testDelete() {
        val mockValue: String? = null
        val mockEditor: SharedPreferences.Editor = mock()
        whenever(preferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.remove(key)).thenReturn(mockEditor)

        defaultPreference.set(mockValue)

        mockEditor verify { remove(key) }
        mockEditor verify { apply() }
        adapter never { set(key, "mockValue", mockEditor) }
    }
}
