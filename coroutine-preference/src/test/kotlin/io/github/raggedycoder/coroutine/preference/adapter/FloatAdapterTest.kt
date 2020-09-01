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
class FloatAdapterTest {

    private val adapter = FloatAdapter
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testKey = "float_key"

    @Test
    fun testGet() {
        val expectedResult = 10.01f
        whenever(preferences.getFloat(testKey, DefaultValues.DEFAULT_FLOAT))
            .thenReturn(expectedResult)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
        preferences verify { getFloat(testKey, DefaultValues.DEFAULT_FLOAT) }
    }

    @Test
    fun testSet() {
        val value = 10.01f
        whenever(editor.putFloat(testKey, value)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putFloat(testKey, value) }
    }
}
