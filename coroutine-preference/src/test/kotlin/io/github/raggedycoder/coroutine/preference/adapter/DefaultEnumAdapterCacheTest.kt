/*
 * Copyright 2020 RaggedyCoder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
