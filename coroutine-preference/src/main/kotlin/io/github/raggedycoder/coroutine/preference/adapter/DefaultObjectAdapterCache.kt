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

import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

@ExperimentalCoroutinesApi
internal object DefaultObjectAdapterCache {

    private val adapterMap: MutableMap<Type, Adapter<*>> = ConcurrentHashMap()

    @Synchronized
    fun <T> getDefault(defaultValue: T, objectType: Type): Adapter<T> {
        if (!adapterMap.containsKey(objectType)) {
            adapterMap[objectType] = DefaultObjectAdapter(objectType, defaultValue)
        }
        return (adapterMap[objectType] as Adapter<T>)
    }
}
