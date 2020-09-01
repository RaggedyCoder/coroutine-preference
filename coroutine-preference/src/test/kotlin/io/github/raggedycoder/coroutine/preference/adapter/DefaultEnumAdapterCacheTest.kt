package io.github.raggedycoder.coroutine.preference.adapter

import io.github.raggedycoder.coroutine.preference.TestEnum
import io.github.raggedycoder.coroutine.preference.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultEnumAdapterCacheTest {

    private val cache = DefaultEnumAdapterCache

    @Test
    fun testGetDefault() {
        val defaultValue = TestEnum.FOO

        val result = cache.getDefault(defaultValue, TestEnum::class.java)
        val secondResult = cache.getDefault(defaultValue, TestEnum::class.java)

        result assertEquals secondResult
    }
}
