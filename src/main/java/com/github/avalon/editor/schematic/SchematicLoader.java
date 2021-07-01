package com.github.avalon.editor.schematic;

import com.github.avalon.nbt.NamedBinaryReader;
import com.github.avalon.nbt.stream.NamedBinaryInputStream;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.resource.data.Resource;

import java.io.InputStream;

public class SchematicLoader {

  public SchematicLoader() {
    try {
      Resource resource = new Resource(null, "ModernHouse.schem");
      InputStream in;
      NamedBinaryReader reader =
          new NamedBinaryReader(new NamedBinaryInputStream(resource.getInputStream()));
      TagCompound compound = reader.read();
      System.out.println("Compound: " + compound.toPrettyString());

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
