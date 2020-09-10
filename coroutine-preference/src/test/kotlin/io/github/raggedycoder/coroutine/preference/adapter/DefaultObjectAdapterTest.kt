package io.github.raggedycoder.coroutine.preference.adapter

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.raggedycoder.coroutine.preference.TestObject
import io.github.raggedycoder.coroutine.preference.assertEquals
import io.github.raggedycoder.coroutine.preference.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

@ExperimentalCoroutinesApi
class DefaultObjectAdapterTest {

    private val type = TestObject::class.java
    private val preferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()
    private val adapter = DefaultObjectAdapter(type, defaultValue, gson)
    private val defaultGsonAdapter = DefaultObjectAdapter(type, defaultValue)

    private val testKey = "object_key"

    @ParameterizedTest(name = "Testing get() for {0} json where the result will be {1}")
    @MethodSource("argumentFactoryMethodForGet")
    fun testGet(mockJson: String?, expectedResult: TestObject) {
        whenever(preferences.getString(testKey, "{}")).thenReturn(mockJson)

        val actualResult = adapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @ParameterizedTest(name = "Testing set() for {1}")
    @MethodSource("argumentFactoryMethodForSet")
    fun testSet(mockJson: String, value: TestObject) {
        whenever(editor.putString(testKey, mockJson)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, mockJson) }
    }

    @ParameterizedTest(name = "Testing get() for {0} json where the result will be {1}")
    @MethodSource("argumentFactoryMethodForGet")
    fun testGetForDefaultGsonAdapter(mockJson: String?, expectedResult: TestObject) {
        whenever(preferences.getString(testKey, "{}")).thenReturn(mockJson)

        val actualResult = defaultGsonAdapter.get(testKey, preferences)

        expectedResult assertEquals actualResult
    }

    @ParameterizedTest(name = "Testing set() for {1}")
    @MethodSource("argumentFactoryMethodForSet")
    fun testSetForDefaultGsonAdapter(mockJson: String, value: TestObject) {
        whenever(editor.putString(testKey, mockJson)).thenReturn(editor)

        adapter.set(testKey, value, editor)

        editor verify { putString(testKey, mockJson) }
    }

    companion object {
        private val gson = Gson()
        private val defaultValue = TestObject(text = "Hello World")

        @JvmStatic
        fun argumentFactoryMethodForGet(): Stream<Arguments> {
            val helloWorldObject = TestObject(text = "Hello World")
            val foobarObject = TestObject(text = "Foobar")
            return Arrays.stream(
                arrayOf(
                    Arguments {
                        arrayOf(
                            null, defaultValue
                        )
                    },
                    Arguments {
                        arrayOf(
                            "{}", defaultValue
                        )
                    },
                    Arguments {
                        arrayOf(
                            gson.toJson(helloWorldObject), helloWorldObject
                        )
                    },
                    Arguments {
                        arrayOf(
                            gson.toJson(foobarObject), foobarObject
                        )
                    }
                )
            )
        }

        @JvmStatic
        fun argumentFactoryMethodForSet(): Stream<Arguments> {
            val helloWorldObject = TestObject(text = "Hello World")
            val foobarObject = TestObject(text = "Foobar")
            return Arrays.stream(
                arrayOf(
                    Arguments {
                        arrayOf(
                            gson.toJson(helloWorldObject), helloWorldObject
                        )
                    },
                    Arguments {
                        arrayOf(
                            gson.toJson(foobarObject), foobarObject
                        )
                    }
                )
            )
        }
    }
}
