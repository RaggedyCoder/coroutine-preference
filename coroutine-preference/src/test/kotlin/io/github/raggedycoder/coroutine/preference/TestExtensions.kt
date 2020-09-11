package io.github.raggedycoder.coroutine.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions

infix fun Any.assertEquals(actualResult: Any) = Assertions.assertEquals(this, actualResult)

infix fun <T> T.verify(func: T.() -> Unit) = verify(this).func()

infix fun <T> T.never(func: T.() -> Unit) = verify(this, never()).func()

val Context.defaultSharedPreferences: SharedPreferences
    get() = getSharedPreferences(defaultSharedPreferenceName, MODE_PRIVATE)

val Context.defaultSharedPreferenceName: String
    get() = packageName + "_preferences"
