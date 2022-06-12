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

package de.natrox.common.validate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckTest {

    @Test
    void notNullTest() {
        Object nullObject = null;
        Object notNullObject = "foo";

        assertThrows(NullPointerException.class, () -> Check.notNull(nullObject, "nullObject"));
        assertDoesNotThrow(() -> Check.notNull(notNullObject, "nullObject"));

        assertThrows(NullPointerException.class, () -> Check.notNull(nullObject, "{0}Object", "null"));
        assertDoesNotThrow(() -> Check.notNull(notNullObject, "{0}Object", "null"));
    }

    @Test
    void argConditionTest() {
        assertThrows(IllegalArgumentException.class, () -> Check.argCondition(true, "argCondition"));
        assertDoesNotThrow(() -> Check.argCondition(false, "argCondition"));

        assertThrows(IllegalArgumentException.class, () -> Check.argCondition(true, "{0}Condition", "arg"));
        assertDoesNotThrow(() -> Check.argCondition(false, "{0}Condition", "arg"));
    }

    @Test
    void failTest() {
        assertThrows(IllegalArgumentException.class, () -> Check.fail("fail"));
    }

    @Test
    void stateConditionTest() {
        assertThrows(IllegalStateException.class, () -> Check.stateCondition(true, "stateCondition"));
        assertDoesNotThrow(() -> Check.argCondition(false, "stateCondition"));

        assertThrows(IllegalStateException.class, () -> Check.stateCondition(true, "{0}Condition", "state"));
        assertDoesNotThrow(() -> Check.argCondition(false, "{0}Condition", "state"));
    }
}
