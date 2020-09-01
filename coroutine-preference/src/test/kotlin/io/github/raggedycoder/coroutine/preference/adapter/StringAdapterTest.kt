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
class StringAdapterTest {

    private val adapter = StringAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "string_key"

    @Test
    fun testGet() {
        val expectedResult = "result"
        whenever(preferences.getString(testKey, DefaultValues.DEFAULT_STRING))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getString(testKey, DefaultValues.DEFAULT_STRING) }
    }

    @Test
    fun testSet() {
        val value = "result"
        whenever(editor.putString(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, value) }
    }
}
