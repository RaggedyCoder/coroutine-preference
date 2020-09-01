package io.github.raggedycoder.coroutine.preference.adapter

import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

@ExperimentalCoroutinesApi
object DefaultObjectAdapterCache {

    private val adapterMap: MutableMap<Type, Adapter<*>> = ConcurrentHashMap()

    @Synchronized
    fun <T> getDefault(defaultValue: T, objectType: Type): Adapter<T> {
        if (!adapterMap.containsKey(objectType)) {
            adapterMap[objectType] = DefaultObjectAdapter(objectType, defaultValue)
        }
        return (adapterMap[objectType] as Adapter<T>)
    }
}
