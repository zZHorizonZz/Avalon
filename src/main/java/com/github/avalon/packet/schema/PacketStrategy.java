package com.github.avalon.packet.schema;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketStrategy {

  private final List<FunctionScheme<?>> schemes;

  public PacketStrategy(FunctionScheme<?>... schemes) {
    this.schemes = new LinkedList<>(Arrays.asList(schemes));
  }

  public List<FunctionScheme<?>> getSchemes() {
    return schemes;
  }

  @Override
  public String toString() {
    String builder =
        schemes.stream()
            .map(
                function ->
                    Character.LINE_SEPARATOR
                        + "  Getter: "
                        + function.getFunction().toString()
                        + " ,Setter"
                        + function.setFunction().toString())
            .collect(Collectors.joining("", "Packet Scheme:", ""));

    return builder;
  }
}
