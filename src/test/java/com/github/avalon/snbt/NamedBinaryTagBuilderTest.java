package com.github.avalon.snbt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.avalon.snbt.parent.NodeSingle;
import org.junit.jupiter.api.Test;

public class NamedBinaryTagBuilderTest {
    @Test
    public void testToNbt() {
        NamedBinaryTagBuilder namedBinaryTagBuilder = new NamedBinaryTagBuilder();
        assertEquals("Name:{}", namedBinaryTagBuilder.toNamedBinaryTag(new NodeSingle("Name", 1)));
    }

    @Test
    public void testToNbt1() {
        assertThrows(IllegalArgumentException.class, () -> (new NamedBinaryTagBuilder()).toNamedBinaryTag(null));
    }

    @Test
    public void testFromNbt() {
        assertNull((new NamedBinaryTagBuilder()).fromNamedBinaryTag("Nbt"));
        assertThrows(IllegalArgumentException.class, () -> (new NamedBinaryTagBuilder()).fromNamedBinaryTag(""));
    }
}

