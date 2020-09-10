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
    fun testSet(value:TestEnum) {
        whenever(editor.putString(testKey, value.name)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, value.name) }
    }
}
