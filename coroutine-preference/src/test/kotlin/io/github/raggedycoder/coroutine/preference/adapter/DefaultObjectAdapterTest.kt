package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.raggedycoder.coroutine.preference.TestObject
import io.github.raggedycoder.coroutine.preference.assertEquals
import io.github.raggedycoder.coroutine.preference.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultObjectAdapterTest {

    private val defaultValue = TestObject(text = "Hello World")
    private val type = TestObject::class.java
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()
    private val gson = Gson()
    private val adapter = DefaultObjectAdapter(type, defaultValue, gson)
    private val defaultGsonAdapter = DefaultObjectAdapter(type, defaultValue)

    private val testKey = "object_key"

    @Test
    fun testGetForDefaultValueWithNullObject() {
        val expectedResult = defaultValue
        whenever(preferences.getString(testKey, "{}")).thenReturn(null)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @Test
    fun testGetForDefaultValueWithNonNullObject() {
        val expectedResult = defaultValue
        whenever(preferences.getString(testKey, "{}")).thenReturn("{}")

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @Test
    fun testGetForNonDefaultValue() {
        val expectedResult = TestObject(text = "Foobar")
        val mockJson = "{\"text\":\"Foobar\"}"
        whenever(preferences.getString(testKey, "{}")).thenReturn(mockJson)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @Test
    fun testSet() {
        val value = TestObject(text = "Foobar")
        val mockJson = "{\"text\":\"Foobar\"}"
        whenever(gson.toJson(value)).thenReturn(mockJson)
        whenever(editor.putString(testKey, mockJson)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, mockJson) }
    }

    @Test
    fun testGetForDefaultValueWithNullObjectForDefaultGsonAdapter() {
        val expectedResult = defaultValue
        whenever(preferences.getString(testKey, "{}")).thenReturn(null)

        val actualResult = defaultGsonAdapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @Test
    fun testGetForDefaultValueWithNonNullObjectForDefaultGsonAdapter() {
        val expectedResult = defaultValue
        whenever(preferences.getString(testKey, "{}")).thenReturn("{}")

        val actualResult = defaultGsonAdapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @Test
    fun testGetForNonDefaultValueForDefaultGsonAdapter() {
        val expectedResult = TestObject(text = "Foobar")
        val mockJson = "{\"text\":\"Foobar\"}"
        whenever(preferences.getString(testKey, "{}")).thenReturn(mockJson)

        val actualResult = defaultGsonAdapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @Test
    fun testSetForDefaultGsonAdapter() {
        val value = TestObject(text = "Foobar")
        val mockJson = "{\"text\":\"Foobar\"}"
        whenever(editor.putString(testKey, mockJson)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, mockJson) }
    }
}
