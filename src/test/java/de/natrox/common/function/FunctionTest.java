/*
 * Copyright 2020-2022 NatroxMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.natrox.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    @Test
    void triFunctionTest() {
        TriFunction<Byte, Short, Float, String> function = this::concat;
        assertEquals("121234.5", function.apply((byte) 12, (short) 123, 4.5F));
        assertEquals('4', function.andThen(this::firstChar).apply((byte) 4, (short) 1, 0.2F));
        assertThrows(RuntimeException.class, () -> function.andThen(this::runtimeException).apply((byte) 4, (short) 1, 0.2F));
    }

    @Test
    void quadFunctionTest() {
        QuadFunction<Long, Integer, Float, Character, String> function = this::concat;
        assertEquals("123456789101234.5F", function.apply(12345678910L, 123, 4.5F, 'F'));
        assertEquals('4', function.andThen(this::firstChar).apply(4L, 1, 0.2F, 'C'));
        assertThrows(RuntimeException.class, () -> function.andThen(this::runtimeException).apply(4L, 1, 0.2F, 'C'));
    }

    @Test
    void throwableFunctionTest() throws Exception {
        {
            ThrowableFunction<String, Character, Exception> function = this::firstChar;
            assertEquals('f', function.apply("foo"));
            assertDoesNotThrow(() -> function.apply("foo"));
        }
        {
            ThrowableFunction<String, Character, Exception> function = this::exception;
            assertThrows(Exception.class, () -> function.apply("foo"));
        }
    }

    @Test
    void throwableBiFunctionTest() throws Exception {
        {
            ThrowableBiFunction<String, String, Character, Exception> function = this::firstChar;
            assertEquals('f', function.apply("foo", "fuu"));
            assertDoesNotThrow(() -> function.apply("foo", "fuu"));
        }
        {
            ThrowableBiFunction<String, String, Character, Exception> function = this::exception;
            assertThrows(Exception.class, () -> function.apply("foo", "fuu"));
        }
    }

    @Test
    void catchingFunctionTest() throws Exception {
        {
            CatchingFunction<String, Character> function = new CatchingFunction<>(this::firstChar);
            assertEquals('f', function.apply("foo"));
            assertDoesNotThrow(() -> function.apply("foo"));
        }
        {
            CatchingFunction<String, Character> function = new CatchingFunction<>(this::runtimeException);
            assertThrows(Exception.class, () -> function.apply("foo"));
        }
    }

    char runtimeException(String... a) {
        throw new RuntimeException();
    }

    char exception(String... a) throws Exception {
        throw new Exception();
    }

    char firstChar(String a, Object... objects) {
        return a.charAt(0);
    }

    String concat(Object... objects) {
        StringBuilder builder = new StringBuilder();
        for (Object object : objects)
            builder.append(object.toString());
        return builder.toString();
    }
}