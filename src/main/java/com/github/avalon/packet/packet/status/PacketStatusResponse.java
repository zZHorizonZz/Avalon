package com.github.avalon.packet.packet.status;

import com.github.avalon.common.data.DataType;
import com.github.avalon.common.status.ServerStatusFactory;
import com.github.avalon.chat.message.Message;
import com.github.avalon.chat.message.Text;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Packet status response is used for sending servers response after receiving {@link
 * PacketStatusRequest} packet. Response is sent in response format. We can send game version,
 * protocol version, maximum players, current players, sample of players on server(can be edited so
 * we can see some message), motd, and icon converted to base64 format.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Json response.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x00,
    protocolType = ProtocolType.STATUS,
    direction = PacketRegister.Direction.CLIENT)
public class PacketStatusResponse extends Packet<PacketStatusResponse> {

  private Gson gson =
      new GsonBuilder()
          .registerTypeAdapter(ServerStatusFactory.class, new ServerStatusFactory())
          .registerTypeAdapter(Text.class, new Text())
          .registerTypeAdapter(Message.class, new Message.Serialization())
          .create();

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.STRING, this::getResponse, this::setResponse));

  private String response;

  public PacketStatusResponse(ServerStatusFactory factory) {
    response = gson.toJson(factory);
  }

  public PacketStatusResponse(String json) {
    response = json;
  }

  public PacketStatusResponse() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketStatusResponse packetStatusResponse) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public Gson getGson() {
    return gson;
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }
}
