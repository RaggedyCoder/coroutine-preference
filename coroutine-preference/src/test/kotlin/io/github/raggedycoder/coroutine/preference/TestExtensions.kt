package io.github.raggedycoder.coroutine.preference

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.never
import org.junit.jupiter.api.Assertions

infix fun Any.assertEquals(actualResult: Any) = Assertions.assertEquals(this, actualResult)

infix fun <T> T.verify(func: T.() -> Unit) = verify(this).func()

infix fun <T> T.never(func: T.() -> Unit) = verify(this, never()).func()
