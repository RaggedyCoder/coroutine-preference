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
class IntAdapterTest {

    private val adapter = IntAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "int_key"

    @ParameterizedTest(name = "Testing for value {0}")
    @ValueSource(ints = [Int.MIN_VALUE, 1001, 1001, Int.MAX_VALUE])
    @DisplayName("Test Get Function")
    fun testGet(expectedResult: Int) {
        whenever(preferences.getInt(testKey, DefaultValues.DEFAULT_INT))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getInt(testKey, DefaultValues.DEFAULT_INT) }
    }

    @ParameterizedTest(name = "Testing for value {0}")
    @ValueSource(ints = [Int.MIN_VALUE, 1001, 1001, Int.MAX_VALUE])
    @DisplayName("Test Set Function")
    fun testSet(value: Int) {
        whenever(editor.putInt(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putInt(testKey, value) }
    }
}
