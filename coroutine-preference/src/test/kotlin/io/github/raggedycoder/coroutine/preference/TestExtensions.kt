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
