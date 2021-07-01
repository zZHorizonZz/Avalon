package com.github.avalon.network.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;

@ChannelHandler.Sharable
public final class NoopHandler extends ChannelHandlerAdapter {

  public static final NoopHandler INSTANCE = new NoopHandler();

  private NoopHandler() {}
}
