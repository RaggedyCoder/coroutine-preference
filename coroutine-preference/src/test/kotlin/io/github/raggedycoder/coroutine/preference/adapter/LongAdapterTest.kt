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
class LongAdapterTest {

    private val adapter = LongAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "long_key"

    @Test
    fun testGet() {
        val expectedResult = 1001L
        whenever(preferences.getLong(testKey, DefaultValues.DEFAULT_LONG))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getLong(testKey, DefaultValues.DEFAULT_LONG) }
    }

    @Test
    fun testSet() {
        val value = 1001L
        whenever(editor.putLong(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putLong(testKey, value) }
    }
}
