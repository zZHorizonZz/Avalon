package com.github.avalon.network.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.SecretKey;

public class EncryptionHandler implements ChannelHandler {

  public EncryptionHandler(SecretKey sharedSecret) {}

  @Override
  public void handlerAdded(ChannelHandlerContext channelHandlerContext) {}

  @Override
  public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {}

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {}
}
