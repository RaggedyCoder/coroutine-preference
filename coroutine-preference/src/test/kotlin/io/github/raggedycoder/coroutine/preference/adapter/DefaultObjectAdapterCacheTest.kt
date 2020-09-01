package io.github.raggedycoder.coroutine.preference.adapter

import io.github.raggedycoder.coroutine.preference.TestObject
import io.github.raggedycoder.coroutine.preference.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultObjectAdapterCacheTest {

    private val cache = DefaultObjectAdapterCache

    @Test
    fun testGetDefault() {
        val defaultValue = TestObject(text = "Foobar")

        val result = cache.getDefault(defaultValue, TestObject::class.java)
        val secondResult = cache.getDefault(defaultValue, TestObject::class.java)

        result assertEquals secondResult
    }
}
