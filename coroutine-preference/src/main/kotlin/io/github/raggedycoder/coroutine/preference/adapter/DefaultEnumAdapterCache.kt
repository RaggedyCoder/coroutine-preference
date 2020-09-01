package io.github.raggedycoder.coroutine.preference.adapter

import io.github.raggedycoder.coroutine.preference.Preference.Adapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.ConcurrentHashMap

@ExperimentalCoroutinesApi
object DefaultEnumAdapterCache {

    private val adapterMap: MutableMap<Class<out Enum<*>>, Adapter<out Enum<*>>> =
        ConcurrentHashMap()

    @Synchronized
    fun <T : Enum<T>> getDefault(defaultValue: T, enumClass: Class<T>): Adapter<T> {
        if (!adapterMap.containsKey(enumClass)) {
            adapterMap[enumClass] = DefaultEnumAdapter(enumClass, defaultValue)
        }
        return (adapterMap[enumClass] as Adapter<T>)
    }
}
