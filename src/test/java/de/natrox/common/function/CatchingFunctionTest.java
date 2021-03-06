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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CatchingFunctionTest {

    @Test
    void defaultApplyTest() {
        CatchingFunction<Integer, Integer> function = new CatchingFunction<>(this::value);
        assertEquals(1, function.apply(1), "Function should return the input of 1");
        assertEquals(2, function.apply(2), "Function should return the input of 2");
    }

    @Test
    void exceptionApplyTest() {
        CatchingFunction<Integer, Integer> function = new CatchingFunction<>(this::value);
        assertThrows(IllegalArgumentException.class, () ->
            function.apply(-1), "Function should throw an exception if the arguments don't meet the conditions");
    }

    private int value(int a) {
        if (a <= 0)
            throw new IllegalArgumentException();
        return a;
    }
}
