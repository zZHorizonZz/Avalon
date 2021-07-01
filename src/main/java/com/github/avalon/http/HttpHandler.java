package com.github.avalon.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;

import java.nio.charset.StandardCharsets;

public class HttpHandler extends SimpleChannelInboundHandler<Object> {

  private final HttpCallback callback;
  private final StringBuilder content = new StringBuilder();

  public HttpHandler(HttpCallback callback) {
    this.callback = callback;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    try {
      callback.onError(cause);
    } finally {
      content.setLength(0);
      ctx.channel().close();
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof HttpResponse) {
      HttpResponse response = (HttpResponse) msg;
      int responseCode = response.getStatus().code();

      if (responseCode == HttpResponseStatus.NO_CONTENT.code()) {
        done(ctx);
        return;
      }

      if (responseCode != HttpResponseStatus.OK.code()) {
        throw new IllegalStateException("Expected HTTP response 200 OK, got " + responseCode);
      }
    }

    if (msg instanceof HttpContent) {
      HttpContent httpContent = (HttpContent) msg;
      content.append(httpContent.content().toString(StandardCharsets.UTF_8));

      if (msg instanceof LastHttpContent) {
        done(ctx);
      }
    }
  }

  private void done(ChannelHandlerContext ctx) {
    try {
      callback.onResponse(content.toString());
    } finally {
      content.setLength(0);
      ctx.channel().close();
    }
  }
}
