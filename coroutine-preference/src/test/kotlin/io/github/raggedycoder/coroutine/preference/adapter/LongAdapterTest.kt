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
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.raggedycoder.coroutine.preference.DefaultValues
import io.github.raggedycoder.coroutine.preference.assertEquals
import io.github.raggedycoder.coroutine.preference.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExperimentalCoroutinesApi
class LongAdapterTest {

    private val adapter = LongAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "long_key"

    @ParameterizedTest(name = "Testing for value {0}")
    @ValueSource(longs = [Long.MIN_VALUE, 1001, 1001, Long.MAX_VALUE])
    @DisplayName("Test Get Function")
    fun testGet(expectedResult: Long) {
        whenever(preferences.getLong(testKey, DefaultValues.DEFAULT_LONG))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getLong(testKey, DefaultValues.DEFAULT_LONG) }
    }

    @ParameterizedTest(name = "Testing for value {0}")
    @ValueSource(longs = [Long.MIN_VALUE, 1001, 1001, Long.MAX_VALUE])
    @DisplayName("Test Set Function")
    fun testSet(value: Long) {
        whenever(editor.putLong(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putLong(testKey, value) }
    }
}
