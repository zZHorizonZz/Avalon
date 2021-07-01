package com.github.avalon.network.handler;

import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.service.HandlerLookupService;

import java.util.HashMap;
import java.util.Map;

/**
 * This is own implementation of of the {@link HandlerLookupService}
 *
 * @author Team of flowpowered. Edited by Horizon.
 * @version 1.0
 */
public class NetworkHandlerService {

  private final Map<Class<? extends Message>, MessageHandler<?, ?>> handlers = new HashMap<>();

  public NetworkHandlerService() {}

  public <M extends Message, H extends MessageHandler<?, ? super M>> void bind(
          Class<M> clazz, Class<H> handlerClass) throws InstantiationException, IllegalAccessException {
    MessageHandler<?, ? super M> handler = handlerClass.newInstance();
    handlers.put(clazz, handler);
  }

  public <M extends Message, H extends MessageHandler<?, ? super M>> void bind(
          Class<M> clazz, H handlerClass) {
    handlers.put(clazz, handlerClass);
  }

  public <M extends Message> MessageHandler<?, M> find(Class<M> clazz) {
    return (MessageHandler) handlers.get(clazz);
  }

  @Override
  public String toString() {
    return "HandlerLookupService{handlers=" + handlers + '}';
  }
}
