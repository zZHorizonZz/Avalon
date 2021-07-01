package com.github.avalon.packet.annotation;

import com.github.avalon.network.ProtocolType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PacketRegister {

  String name() default "PacketRegister";

  int packetPriority() default 100;

  int operationCode();

  ProtocolType protocolType();

  Direction direction();

  enum Direction {
    MULTI,
    CLIENT,
    SERVER
  }
}
