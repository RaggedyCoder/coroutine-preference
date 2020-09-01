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
class IntAdapterTest {

    private val adapter = IntAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "int_key"

    @Test
    fun testGet() {
        val expectedResult = 1001
        whenever(preferences.getInt(testKey, DefaultValues.DEFAULT_INT))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify {getInt(testKey, DefaultValues.DEFAULT_INT) }
    }

    @Test
    fun testSet() {
        val value = 1001
        whenever(editor.putInt(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putInt(testKey, value) }
    }
}
