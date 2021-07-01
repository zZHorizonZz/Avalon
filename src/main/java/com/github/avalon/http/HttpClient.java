package com.github.avalon.http;

import com.github.avalon.common.system.UtilNetwork;
import com.google.common.net.HttpHeaders;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class HttpClient {

  /**
   * Opens a URL.
   *
   * @param url the URL to download
   * @param eventLoop an {@link EventLoop} that will receive the response body
   * @param callback a callback to handle the response or any onError
   */
  public void connect(String url, EventLoop eventLoop, HttpCallback callback) {

    URI uri = URI.create(url);

    String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
    String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
    int port = uri.getPort();

    SslContext sslCtx = null;
    if ("https".equalsIgnoreCase(scheme)) {
      if (port == -1) {
        port = 443;
      }
      try {
        sslCtx =
            SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
      } catch (SSLException e) {
        callback.onError(e);
        return;
      }
    } else if ("http".equalsIgnoreCase(scheme)) {
      if (port == -1) {
        port = 80;
      }
    } else {
      throw new IllegalArgumentException("Only http(s) is supported!");
    }

    new Bootstrap()
        .group(eventLoop)
        .channel(UtilNetwork.bestSocketChannel())
        .handler(new HttpChannelInitializer(sslCtx, callback))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .connect(new InetSocketAddress(host, port))
        .addListener(
            (ChannelFutureListener)
                future -> {
                  if (future.isSuccess()) {
                    String path =
                        uri.getRawPath()
                            + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery());
                    HttpRequest request =
                        new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
                    request.headers().set(HttpHeaders.HOST, host);
                    future.channel().writeAndFlush(request);
                  } else {
                    callback.onError(future.cause());
                  }
                });
  }

  private static class HttpChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext sslCtx;
    private final HttpCallback callback;

    public HttpChannelInitializer(SslContext sslCtx, HttpCallback callback) {
      this.sslCtx = sslCtx;
      this.callback = callback;
    }

    @Override
    protected void initChannel(Channel channel) {
      channel.pipeline().addLast("timeout", new ReadTimeoutHandler(6000, TimeUnit.MILLISECONDS));
      if (sslCtx != null) {
        channel.pipeline().addLast("ssl", sslCtx.newHandler(channel.alloc()));
      }
      channel.pipeline().addLast("codec", new HttpClientCodec());
      channel.pipeline().addLast("handler", new HttpHandler(callback));
    }

    public HttpCallback getCallback() {
      return callback;
    }

    public SslContext getSslCtx() {
      return sslCtx;
    }
  }
}
