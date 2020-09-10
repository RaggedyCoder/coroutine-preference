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
class StringAdapterTest {

    private val adapter = StringAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "string_key"

    @ParameterizedTest(name = "Testing for value {0}")
    @ValueSource(strings = ["", "result"])
    @DisplayName("Test Get Function")
    fun testGet(expectedResult: String) {
        whenever(preferences.getString(testKey, DefaultValues.DEFAULT_STRING))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getString(testKey, DefaultValues.DEFAULT_STRING) }
    }

    @ParameterizedTest(name = "Testing for value {0}")
    @ValueSource(strings = ["", "result"])
    @DisplayName("Test Set Function")
    fun testSet(value: String) {
        whenever(editor.putString(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, value) }
    }
}
