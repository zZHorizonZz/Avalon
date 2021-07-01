package com.github.avalon.network.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

public class CompressionHandler implements ChannelHandler {

  public CompressionHandler(int threshold) {}

  @Override
  public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {}

  @Override
  public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {}

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable)
      throws Exception {}
}
