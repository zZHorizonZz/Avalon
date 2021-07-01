package com.github.avalon.nbt.serialization;

import com.github.avalon.nbt.tag.Tag;

public interface NamedBinarySerializer {

    Tag serialize(Object object);
}
