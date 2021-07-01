package com.github.avalon.network.handler.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LegacyHandshakeHandler extends ChannelInboundHandlerAdapter {

  // GameServer networkServer;

  public LegacyHandshakeHandler() {}

  @Override
  public void channelRead(ChannelHandlerContext channelhandlercontext, Object object) {
    ByteBuf bytebuf = (ByteBuf) object;

    bytebuf.markReaderIndex();
    boolean flag = true;

    try {
      if (bytebuf.readUnsignedByte() != 254) {
        return;
      }

      InetSocketAddress inetsocketaddress =
          (InetSocketAddress) channelhandlercontext.channel().remoteAddress();
      // MinecraftServer minecraftserver = this.b.d();
      int i = bytebuf.readableBytes();
      String s;
      // org.bukkit.event.server.ServerListPingEvent event =
      // org.bukkit.craftbukkit.event.CraftEventFactory.callServerListPingEvent(minecraftserver.server, inetsocketaddress.getAddress(), minecraftserver.getMotd(), minecraftserver.getPlayerCount(), minecraftserver.getMaxPlayers()); // CraftBukkit

      switch (i) {
        case 0:
          System.out.println("Ping: (<1.3.x) from {}:{}");
          // LegacyPingHandler.LOGGER.debug("Ping: (<1.3.x) from {}:{}",
          // inetsocketaddress.getAddress(), inetsocketaddress.getPort());
          s = String.format("%s\u00a7%d\u00a7%d", "Ejeheeef", 56, 86); // CraftBukkit
          a(channelhandlercontext, a(s));
          break;
        case 1:
          if (bytebuf.readUnsignedByte() != 1) {
            return;
          }

          System.out.println("Ping: (1.4-1.5.x) from {}:{}");
          // LegacyPingHandler.LOGGER.debug("Ping: (1.4-1.5.x) from {}:{}",
          // inetsocketaddress.getAddress(), inetsocketaddress.getPort());
          s =
              String.format(
                  "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                  127, 112, "poibefef", 32, 124); // CraftBukkit
          a(channelhandlercontext, a(s));
          break;
        default:
          boolean flag1 = bytebuf.readUnsignedByte() == 1;

          flag1 &= bytebuf.readUnsignedByte() == 250;
          flag1 &=
              "MC|PingHost"
                  .equals(
                      new String(
                          bytebuf.readBytes(bytebuf.readShort() * 2).array(),
                          StandardCharsets.UTF_16BE));
          int j = bytebuf.readUnsignedShort();

          flag1 &= bytebuf.readUnsignedByte() >= 73;
          flag1 &= 3 + bytebuf.readBytes(bytebuf.readShort() * 2).array().length + 4 == j;
          flag1 &= bytebuf.readInt() <= 65535;
          flag1 &= bytebuf.readableBytes() == 0;
          if (!flag1) {
            return;
          }

          System.out.println("Ping: (1.6) from {}:{}");
          // LegacyPingHandler.LOGGER.debug("Ping: (1.6) from {}:{}",
          // inetsocketaddress.getAddress(), inetsocketaddress.getPort());
          String s1 =
              String.format(
                  "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                  127, 52, "Eeeeeee", 56, 86); // CraftBukkit
          ByteBuf bytebuf1 = a(s1);

          try {
            a(channelhandlercontext, bytebuf1);
          } finally {
            bytebuf1.release();
          }
      }

      bytebuf.release();
      flag = false;
    } catch (RuntimeException ignored) {
    } finally {
      if (flag) {
        bytebuf.resetReaderIndex();
        channelhandlercontext.channel().pipeline().remove("legacy_version");
        channelhandlercontext.fireChannelRead(object);
      }
    }
  }

  private void a(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) {
    channelhandlercontext
        .pipeline()
        .firstContext()
        .writeAndFlush(bytebuf)
        .addListener(ChannelFutureListener.CLOSE);
  }

  private ByteBuf a(String s) {
    ByteBuf bytebuf = Unpooled.buffer();

    bytebuf.writeByte(255);
    char[] achar = s.toCharArray();

    bytebuf.writeShort(achar.length);
    char[] achar1 = achar;
    int i = achar.length;

    for (int j = 0; j < i; ++j) {
      char c0 = achar1[j];

      bytebuf.writeChar(c0);
    }

    return bytebuf;
  }
}
