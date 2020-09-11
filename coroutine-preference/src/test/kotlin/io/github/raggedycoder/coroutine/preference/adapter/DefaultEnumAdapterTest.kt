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
import io.github.raggedycoder.coroutine.preference.TestEnum
import io.github.raggedycoder.coroutine.preference.assertEquals
import io.github.raggedycoder.coroutine.preference.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExperimentalCoroutinesApi
class DefaultEnumAdapterTest {

    private val defaultValue = TestEnum.FOO
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()
    private val adapter = DefaultEnumAdapter(TestEnum::class.java, defaultValue)

    private val testKey = "enum_key"

    @Test
    fun testGetForDefaultValue() {
        val expectedResult = defaultValue
        whenever(preferences.getString(testKey, null)).thenReturn(null)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @ParameterizedTest
    @EnumSource(value = TestEnum::class, names = ["FOO", "BAR"])
    fun testGetForNonDefaultValue(expectedResult: TestEnum) {
        whenever(preferences.getString(testKey, null)).thenReturn(expectedResult.name)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @ParameterizedTest
    @EnumSource(value = TestEnum::class, names = ["FOO", "BAR"])
    fun testSet(value: TestEnum) {
        whenever(editor.putString(testKey, value.name)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, value.name) }
    }
}
