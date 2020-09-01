package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.raggedycoder.coroutine.preference.DefaultValues
import io.github.raggedycoder.coroutine.preference.assertEquals
import io.github.raggedycoder.coroutine.preference.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class StringSetAdapterTest {

    private val adapter = StringSetAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "string_set_key"

    @Test
    fun testGet() {
        val expectedResult = setOf("result")
        whenever(preferences.getStringSet(testKey, DefaultValues.DEFAULT_STRING_SET))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getStringSet(testKey, DefaultValues.DEFAULT_STRING_SET) }
    }

    @Test
    fun testSet() {
        val value = setOf("result")
        whenever(editor.putStringSet(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putStringSet(testKey, value) }
    }
}
