package de.natrox.common.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UuidUtilTest {

    @Test
    void fromNameTest() {
        assertEquals(UuidUtil.fromName("foo"), UuidUtil.fromName("foo"));
        assertNotEquals(UuidUtil.fromName("foo"), UuidUtil.fromName("boo"));
        assertThrows(NullPointerException.class, () -> UuidUtil.fromName(null));
        assertDoesNotThrow(() -> UuidUtil.fromName(""));
    }
}
