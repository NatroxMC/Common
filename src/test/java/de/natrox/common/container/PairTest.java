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

package de.natrox.common.container;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PairTest {

    @Test
    public void equalsTest() {
        Pair<String, String> pair = Pair.of("first", "second");
        assertNotEquals(Pair.empty(), pair);
    }

    @Test
    public void equalsTest2() {
        Pair<?, ?> pair = Pair.empty();
        assertEquals(Pair.empty(), pair);
    }

    @Test
    public void equalsTest3() {
        Pair<String, String> pair = Pair.of("first", "second");
        assertEquals(Pair.of("first", "second"), pair);
    }

    @Test
    public void equalsTest4() {
        assertNotEquals("o", Pair.empty());
    }

    @Test
    public void equalsTest5() {
        Pair<String, Pair<?, ?>> pair = Pair.of("first", Pair.empty());
        assertNotEquals(Pair.of("first", "second"), pair);
    }

    @Test
    public void equalsTest6() {
        Pair<Pair<?, ?>, String> pair = Pair.of(Pair.empty(), "second");
        assertNotEquals(Pair.empty(), pair);
    }

    @Test
    public void equalsTest7() {
        Pair<?, String> pair = Pair.of(null, "second");
        assertNotEquals(Pair.empty(), pair);
    }

    @Test
    public void setFirstTest() {
        Pair<String, String> pair = Pair.empty();
        pair.setFirst("first");
        assertEquals("first", pair.first());
    }

    @Test
    public void setSecondTest() {
        Pair<String, String> pair = Pair.empty();
        pair.setSecond("second");
        assertEquals("second", pair.second());
    }
}
