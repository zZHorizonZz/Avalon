package com.github.avalon.packet.packet.play;

import com.github.avalon.common.bytes.BitField;
import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * This packet is sent by client to server and contains players current setting or at least settings
 * that should contains because modded clients can have different settings nad packet settings.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Player's language.
 *   <li>2. Player's view distance in chunks.
 *   <li>3. ChatOperator mode 0: enabled, 1: commands only, 2: hidden. according to <url>www.wiki.vg</url>
 *   <li>4. If player has enabled colors in chat.
 *   <li>6. Player's showed skin parts.
 *   <li>7. Player's hand (Right, Left)
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x05,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketClientSettings extends Packet<PacketClientSettings> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.STRING, this::getLocale, this::setLocale),
          new FunctionScheme<>(DataType.BYTE, this::getViewDistance, this::setViewDistance),
          new FunctionScheme<>(DataType.VARINT, this::getChatMode, this::setChatMode),
          new FunctionScheme<>(DataType.BOOLEAN, this::isColors, this::setColors),
          new FunctionScheme<>(DataType.BIT_FIELD, this::getSkinParts, this::setSkinParts),
          new FunctionScheme<>(DataType.VARINT, this::getMainHand, this::setMainHand));

  private String locale;
  private byte viewDistance;
  private int chatMode;
  private boolean colors;
  private BitField skinParts;
  private int mainHand;

  public PacketClientSettings() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketClientSettings packetClientSettings) {
    /*if (connection.isActive()) {
      PlayerSettings settings = connection.getPlayer().getSettings();
      settings.setLocale(packetClientSettings.getSettings().getLocale());
      settings.setChatMode(packetClientSettings.getSettings().getChatMode());
      settings.setColors(packetClientSettings.getSettings().isColors());
      settings.setRightHand(packetClientSettings.getSettings().isRightHand());
      settings.setHat(packetClientSettings.getSettings().hasHat());
      settings.setLeftPants(packetClientSettings.getSettings().hasLeftPants());
      settings.setRightPants(packetClientSettings.getSettings().hasRightPants());
      settings.setCape(packetClientSettings.getSettings().hasCape());
      settings.setJacket(packetClientSettings.getSettings().hasJacket());
      settings.setLeftSleeve(packetClientSettings.getSettings().hasLeftSleeve());
      settings.setRightSleeve(packetClientSettings.getSettings().hasRightSleeve());
    } else {
      connection.disconnect("We can not properly recognize your settings.");
    }*/
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public byte getViewDistance() {
    return viewDistance;
  }

  public void setViewDistance(byte viewDistance) {
    this.viewDistance = viewDistance;
  }

  public int getChatMode() {
    return chatMode;
  }

  public void setChatMode(int chatMode) {
    this.chatMode = chatMode;
  }

  public boolean isColors() {
    return colors;
  }

  public void setColors(boolean colors) {
    this.colors = colors;
  }

  public BitField getSkinParts() {
    return skinParts;
  }

  public void setSkinParts(BitField skinParts) {
    this.skinParts = skinParts;
  }

  public int getMainHand() {
    return mainHand;
  }

  public void setMainHand(int mainHand) {
    this.mainHand = mainHand;
  }
}
