package io.github.raggedycoder.coroutine.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DefaultPreferenceIntegrationTest {

    private lateinit var preferences: SharedPreferences
    private lateinit var coroutineSharedPreferences: CoroutineSharedPreferences

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        preferences = context.defaultSharedPreferences
        preferences.edit().clear().commit()
        coroutineSharedPreferences = CoroutineSharedPreferences
            .Builder()
            .preferences(preferences)
            .build()
    }

    @Test
    fun testKey() {
        val expectedResult = "foo"
        val actualResult = coroutineSharedPreferences.getString(expectedResult).key
        expectedResult assertEquals actualResult
    }

    @Test
    fun testByDefaultDefaultValues() {
        val expectedBooleanResult = false
        val expectedFloatResult = 0f
        val expectedIntResult = 0
        val expectedLongResult = 0L
        val expectedStringResult = ""
        val expectedStringSetResult = setOf<String>()

        coroutineSharedPreferences.getBoolean("default_key_1").defaultValue assertEquals expectedBooleanResult
        coroutineSharedPreferences.getFloat("default_key_2").defaultValue assertEquals expectedFloatResult
        coroutineSharedPreferences.getInt("default_key_3").defaultValue assertEquals expectedIntResult
        coroutineSharedPreferences.getLong("default_key_4").defaultValue assertEquals expectedLongResult
        coroutineSharedPreferences.getString("default_key_5").defaultValue assertEquals expectedStringResult
        coroutineSharedPreferences.getStringSet("default_key_6").defaultValue assertEquals expectedStringSetResult
    }

    @Test
    fun testDefaultValues() {
        val expectedBooleanResult = true
        val expectedFloatResult = 101f
        val expectedIntResult = 1001
        val expectedLongResult = 10001L
        val expectedStringResult = "FooBar"
        val expectedStringSetResult = setOf("foo", "bar")
        val expectedEnumResult = TestEnum.FOO
        val expectedObjectResult = TestObject("FooBar")

        coroutineSharedPreferences.getBoolean(
            "default_key_1",
            expectedBooleanResult
        ).defaultValue assertEquals expectedBooleanResult

        coroutineSharedPreferences.getFloat(
            "default_key_2",
            expectedFloatResult
        ).defaultValue assertEquals expectedFloatResult

        coroutineSharedPreferences.getInt(
            "default_key_3",
            expectedIntResult
        ).defaultValue assertEquals expectedIntResult

        coroutineSharedPreferences.getLong(
            "default_key_4",
            expectedLongResult
        ).defaultValue assertEquals expectedLongResult

        coroutineSharedPreferences.getString(
            "default_key_5",
            expectedStringResult
        ).defaultValue assertEquals expectedStringResult

        coroutineSharedPreferences.getStringSet(
            "default_key_6",
            expectedStringSetResult
        ).defaultValue assertEquals expectedStringSetResult

        coroutineSharedPreferences.getEnum(
            "default_key_7",
            expectedEnumResult,
            TestEnum::class.java
        ).defaultValue assertEquals expectedEnumResult

        coroutineSharedPreferences.getObject(
            "default_key_8",
            expectedObjectResult,
            TestObject::class.java
        ).defaultValue assertEquals expectedObjectResult
    }

    @Test
    fun testWithNoValueReturnsDefaultValue() {
        val expectedBooleanResult = true
        val expectedFloatResult = 101f
        val expectedIntResult = 1001
        val expectedLongResult = 10001L
        val expectedStringResult = "FooBar"
        val expectedStringSetResult = setOf("foo", "bar")
        val expectedEnumResult = TestEnum.FOO
        val expectedObjectResult = TestObject("FooBar")

        coroutineSharedPreferences.getBoolean(
            "default_key_1",
            expectedBooleanResult
        ).value assertEquals expectedBooleanResult

        coroutineSharedPreferences.getFloat(
            "default_key_2",
            expectedFloatResult
        ).value assertEquals expectedFloatResult

        coroutineSharedPreferences.getInt(
            "default_key_3",
            expectedIntResult
        ).value assertEquals expectedIntResult

        coroutineSharedPreferences.getLong(
            "default_key_4",
            expectedLongResult
        ).value assertEquals expectedLongResult

        coroutineSharedPreferences.getString(
            "default_key_5",
            expectedStringResult
        ).value assertEquals expectedStringResult

        coroutineSharedPreferences.getStringSet(
            "default_key_6",
            expectedStringSetResult
        ).value assertEquals expectedStringSetResult

        coroutineSharedPreferences.getEnum(
            "default_key_7",
            expectedEnumResult,
            TestEnum::class.java
        ).value assertEquals expectedEnumResult

        coroutineSharedPreferences.getObject(
            "default_key_8",
            expectedObjectResult,
            TestObject::class.java
        ).value assertEquals expectedObjectResult
    }

    @Test
    fun testWitStoredValueReturnStoredValue() {
        val expectedBooleanResult = true
        val expectedFloatResult = 101f
        val expectedIntResult = 1001
        val expectedLongResult = 10001L
        val expectedStringResult = "Foobar"
        val expectedStringSetResult = setOf("foo", "bar")
        val expectedEnumResult = TestEnum.FOO
        val expectedObjectResult = TestObject("FooBar")

        preferences.edit().putBoolean("default_key_1", expectedBooleanResult).commit()
        preferences.edit().putFloat("default_key_2", expectedFloatResult).commit()
        preferences.edit().putInt("default_key_3", expectedIntResult).commit()
        preferences.edit().putLong("default_key_4", expectedLongResult).commit()
        preferences.edit().putString("default_key_5", expectedStringResult).commit()
        preferences.edit().putStringSet("default_key_6", expectedStringSetResult).commit()
        preferences.edit().putString("default_key_7", TestEnum.FOO.name).commit()
        preferences.edit().putString("default_key_8", Gson().toJson(expectedObjectResult)).commit()


        coroutineSharedPreferences.getBoolean("default_key_1").value assertEquals expectedBooleanResult

        coroutineSharedPreferences.getFloat("default_key_2").value assertEquals expectedFloatResult

        coroutineSharedPreferences.getInt("default_key_3").value assertEquals expectedIntResult

        coroutineSharedPreferences.getLong("default_key_4").value assertEquals expectedLongResult

        coroutineSharedPreferences.getString("default_key_5").value assertEquals expectedStringResult

        coroutineSharedPreferences.getStringSet("default_key_6").value assertEquals expectedStringSetResult

        coroutineSharedPreferences.getEnum(
            "default_key_7",
            TestEnum.BAR,
            TestEnum::class.java
        ).value assertEquals expectedEnumResult

        coroutineSharedPreferences.getObject(
            "default_key_8",
            TestObject("HelloWorld"),
            TestObject::class.java
        ).value assertEquals expectedObjectResult
    }

    @Test
    fun testSet() {
        val expectedBooleanResult = true
        val expectedFloatResult = 101f
        val expectedIntResult = 1001
        val expectedLongResult = 10001L
        val expectedStringResult = "Foobar"
        val expectedStringSetResult = setOf("foo", "bar")
        val expectedEnumResult = TestEnum.FOO
        val expectedObjectResult = TestObject("FooBar")

        val booleanPreference = coroutineSharedPreferences.getBoolean("default_key_1")
        val floatPreference = coroutineSharedPreferences.getFloat("default_key_2")
        val intPreference = coroutineSharedPreferences.getInt("default_key_3")
        val longPreference = coroutineSharedPreferences.getLong("default_key_4")
        val stringPreference = coroutineSharedPreferences.getString("default_key_5")
        val stringSetPreference = coroutineSharedPreferences.getStringSet("default_key_6")
        val enumPreference = coroutineSharedPreferences.getEnum(
            "default_key_7",
            TestEnum.BAR,
            TestEnum::class.java
        )
        val objectPreference = coroutineSharedPreferences.getObject(
            "default_key_8",
            TestObject("HelloWorld"),
            TestObject::class.java
        )

        booleanPreference.set(expectedBooleanResult)
        floatPreference.set(expectedFloatResult)
        intPreference.set(expectedIntResult)
        longPreference.set(expectedLongResult)
        stringPreference.set(expectedStringResult)
        stringSetPreference.set(expectedStringSetResult)
        enumPreference.set(expectedEnumResult)
        objectPreference.set(expectedObjectResult)

        booleanPreference.value assertEquals expectedBooleanResult

        floatPreference.value assertEquals expectedFloatResult

        intPreference.value assertEquals expectedIntResult

        longPreference.value assertEquals expectedLongResult

        stringPreference.value assertEquals expectedStringResult

        stringSetPreference.value assertEquals expectedStringSetResult

        enumPreference.value assertEquals expectedEnumResult

        objectPreference.value assertEquals expectedObjectResult
    }

    @Test
    fun testAvailable() {
        val booleanPreference = coroutineSharedPreferences.getBoolean("default_key_1")

        booleanPreference.available assertEquals false

        preferences.edit().putBoolean("default_key_1", true).commit()

        booleanPreference.available assertEquals true

        preferences.edit().remove("default_key_1").commit()

        booleanPreference.available assertEquals false

    }

    @Test
    fun testDelete() {
        preferences.edit().putBoolean("default_key_1", true).commit()

        val booleanPreference = coroutineSharedPreferences.getBoolean("default_key_1")

        booleanPreference.available assertEquals true

        booleanPreference.delete()

        booleanPreference.available assertEquals false

    }
}
