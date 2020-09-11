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
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultCoroutineSharedPreferencesTest {

    private val sharedPreferences: SharedPreferences = mock()
    private val coroutineSharedPreferences = DefaultCoroutineSharedPreferences(sharedPreferences)

    @Test
    fun testGetBoolean() {
        val testKey = "boolean_key"
        val testDefaultValue = true
        val testAdapter: Preference.Adapter<Boolean> = mock()
        val result = coroutineSharedPreferences.getBoolean(testKey, testDefaultValue, testAdapter)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetBooleanWithDefaults() {
        val testKey = "boolean_key"
        val result = coroutineSharedPreferences.getBoolean(testKey)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetInt() {
        val testKey = "int_key"
        val testDefaultValue = 1010
        val testAdapter: Preference.Adapter<Int> = mock()
        val result = coroutineSharedPreferences.getInt(testKey, testDefaultValue, testAdapter)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetIntWithDefaults() {
        val testKey = "int_key"
        val result = coroutineSharedPreferences.getInt(testKey)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetLong() {
        val testKey = "long_key"
        val testDefaultValue = 1010L
        val testAdapter: Preference.Adapter<Long> = mock()
        val result = coroutineSharedPreferences.getLong(testKey, testDefaultValue, testAdapter)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetLongWithDefaults() {
        val testKey = "long_key"
        val result = coroutineSharedPreferences.getLong(testKey)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetFloat() {
        val testKey = "float_key"
        val testDefaultValue = 10.10f
        val testAdapter: Preference.Adapter<Float> = mock()
        val result = coroutineSharedPreferences.getFloat(testKey, testDefaultValue, testAdapter)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetFloatWithDefaults() {
        val testKey = "float_key"
        val result = coroutineSharedPreferences.getFloat(testKey)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetString() {
        val testKey = "string_key"
        val testDefaultValue = "string"
        val testAdapter: Preference.Adapter<String> = mock()
        val result = coroutineSharedPreferences.getString(testKey, testDefaultValue, testAdapter)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetStringWithDefaults() {
        val testKey = "string_key"
        val result = coroutineSharedPreferences.getString(testKey)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetStringSet() {
        val testKey = "string_set_key"
        val testDefaultValue = setOf<String>()
        val testAdapter: Preference.Adapter<Set<String>> = mock()
        val result = coroutineSharedPreferences.getStringSet(testKey, testDefaultValue, testAdapter)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetStringSetWithDefaults() {
        val testKey = "string_set_key"
        val result = coroutineSharedPreferences.getStringSet(testKey)

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetEnum() {
        val testKey = "enum_key"
        val testDefaultValue = TestEnum.FOO
        val testAdapter: Preference.Adapter<TestEnum> = mock()
        val result = coroutineSharedPreferences.getEnum(
            testKey,
            testDefaultValue,
            TestEnum::class.java,
            testAdapter
        )

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetEnumWithDefaults() {
        val testKey = "enum_key"
        val testDefaultValue = TestEnum.FOO
        val result = coroutineSharedPreferences.getEnum(
            testKey,
            testDefaultValue,
            TestEnum::class.java
        )

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetObjectWithClass() {
        val testKey = "object_key"
        val testDefaultValue = TestObject(text = "Foobar")
        val testAdapter: Preference.Adapter<TestObject> = mock()
        val result = coroutineSharedPreferences.getObject(
            testKey,
            testDefaultValue,
            TestObject::class.java,
            testAdapter
        )

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetObjectWithClassWithDefaults() {
        val testKey = "object_key"
        val testDefaultValue = TestObject(text = "Foobar")
        val result = coroutineSharedPreferences.getObject(
            testKey,
            testDefaultValue,
            TestObject::class.java
        )

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetObjectWithType() {
        val testKey = "object_key"
        val testDefaultValue = listOf<String>()
        val testAdapter: Preference.Adapter<List<String>> = mock()
        val result = coroutineSharedPreferences.getObject(
            testKey,
            testDefaultValue,
            object : TypeToken<List<String>>() {}.type,
            testAdapter
        )

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testGetObjectWithTypeWithDefaults() {
        val testKey = "object_key"
        val testDefaultValue = listOf<String>()
        val result = coroutineSharedPreferences.getObject(
            testKey,
            testDefaultValue,
            object : TypeToken<List<String>>() {}.type
        )

        assertTrue(result is DefaultPreference)
    }

    @Test
    fun testClear() {
        val mockEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.clear()).thenReturn(mockEditor)

        coroutineSharedPreferences.clear()

        sharedPreferences verify { edit() }
        mockEditor verify {
            clear()
            apply()
        }
    }
}
