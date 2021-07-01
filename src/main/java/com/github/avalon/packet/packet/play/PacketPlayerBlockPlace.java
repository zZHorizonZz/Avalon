package com.github.avalon.packet.packet.play;

import com.github.avalon.block.BlockFace;
import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.EnumScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.Hand;

/**
 * Packet sent by the client to server as information about his current block place. Server should
 * validate this and if is not valid then {@link PacketBlockChange} should be sent as counter
 * packet.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Hand that with player placed block.
 *   <li>2. Position of block converted into long.
 *   <li>3. Face of block that player clicked on.
 *   <li>4. Cursor location on face x axis.
 *   <li>5. Cursor location on face y axis.
 *   <li>6. Cursor location on face z axis.
 *   <li>7. If player's head is inside of block. (IDK why is this here?)
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x2E,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketPlayerBlockPlace extends Packet<PacketPlayerBlockPlace> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new EnumScheme<>(Hand.class, this::getHand, this::setHand),
          new FunctionScheme<>(DataType.POSITION, this::getPosition, this::setPosition),
          new EnumScheme<>(BlockFace.class, this::getFace, this::setFace),
          new FunctionScheme<>(DataType.FLOAT, this::getCursorX, this::setCursorX),
          new FunctionScheme<>(DataType.FLOAT, this::getCursorY, this::setCursorY),
          new FunctionScheme<>(DataType.FLOAT, this::getCursorZ, this::setCursorZ),
          new FunctionScheme<>(DataType.BOOLEAN, this::isInsideBlock, this::setInsideBlock));

  private Hand hand;
  private long position;
  private BlockFace face;
  private float cursorX;
  private float cursorY;
  private float cursorZ;
  private boolean insideBlock;

  public PacketPlayerBlockPlace() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerBlockPlace packet) {
    connection.getPlayer().getServer().getPacketManager().handle(packet, connection);
  }

  public Hand getHand() {
    return hand;
  }

  public void setHand(Hand hand) {
    this.hand = hand;
  }

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }

  public BlockFace getFace() {
    return face;
  }

  public void setFace(BlockFace face) {
    this.face = face;
  }

  public float getCursorX() {
    return cursorX;
  }

  public void setCursorX(float cursorX) {
    this.cursorX = cursorX;
  }

  public float getCursorY() {
    return cursorY;
  }

  public void setCursorY(float cursorY) {
    this.cursorY = cursorY;
  }

  public float getCursorZ() {
    return cursorZ;
  }

  public void setCursorZ(float cursorZ) {
    this.cursorZ = cursorZ;
  }

  public boolean isInsideBlock() {
    return insideBlock;
  }

  public void setInsideBlock(boolean insideBlock) {
    this.insideBlock = insideBlock;
  }
}
