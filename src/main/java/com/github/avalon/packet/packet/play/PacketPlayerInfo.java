package com.github.avalon.packet.packet.play;

import com.github.avalon.account.PlayerProfile;
import com.github.avalon.account.ProfileStatus;
import com.github.avalon.network.PacketBuffer;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.GameMode;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;

/**
 * Sent by the server to update player's list. Also can send players skins, pings, etc. In the
 * future this packet may be changed to to work with {@link PacketStrategy}.
 *
 * <h3>Packet Strategy</h3>
 *
 * <h1>This packet does not have any strategy because it has complex.</h1>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x32,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketPlayerInfo extends Packet<PacketPlayerInfo> {

  private PlayerInfoAction action;

  private String name;
  private PlayerProfile profile;
  private GameMode gameMode;
  private long ping;
  private String displayName;

  private List<PlayerInfo> playerInformation;

  public PacketPlayerInfo(
      PlayerInfoAction action,
      String name,
      PlayerProfile profile,
      GameMode gameMode,
      long ping,
      String displayName) {
    this.action = action;
    this.name = name;
    this.profile = profile;
    this.gameMode = gameMode;
    this.ping = ping;
    this.displayName = displayName;
  }

  public PacketPlayerInfo(PlayerInfoAction action, List<PlayerInfo> playerInformation) {
    this.action = action;
    this.playerInformation = playerInformation;
  }

  public PacketPlayerInfo(
      String name, PlayerProfile profile, GameMode gameMode, long ping, String displayName) {
    action = PlayerInfoAction.ADD_PLAYER;
    this.name = name;
    this.profile = profile;
    this.gameMode = gameMode;
    this.ping = ping;
    this.displayName = displayName;
  }

  public PacketPlayerInfo(GameMode gameMode) {
    action = PlayerInfoAction.UPDATE_GAMEMODE;
    this.gameMode = gameMode;
  }

  public PacketPlayerInfo(long ping) {
    action = PlayerInfoAction.UPDATE_LATENCY;
    this.ping = ping;
  }

  public PacketPlayerInfo(String displayName) {
    action = PlayerInfoAction.UPDATE_DISPLAY_NAME;
    this.displayName = displayName;
  }

  public PacketPlayerInfo(PlayerInfoAction action) {
    this.action = action;
  }

  public PacketPlayerInfo() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public Packet<?> decode(ByteBuf buffer) throws IOException {
    throw new UnsupportedOperationException("This operation is not supported");
  }

  @Override
  public ByteBuf encode(ByteBuf byteBuf, Packet<?> packet) throws IOException {
    PacketPlayerInfo packetPlayerInfo = (PacketPlayerInfo) packet;
    PacketBuffer buffer = (PacketBuffer) byteBuf;

    PlayerInfoAction action = packetPlayerInfo.getAction();
    buffer.writeVarInt(action.getIndex());

    if (packetPlayerInfo.getPlayerInformation() == null
        || packetPlayerInfo.getPlayerInformation().isEmpty()) {
      buffer.writeVarInt(1);

      writeAction(
          buffer,
          action,
          packetPlayerInfo.getName(),
          packetPlayerInfo.getProfile(),
          packetPlayerInfo.getGameMode(),
          packetPlayerInfo.getPing(),
          packetPlayerInfo.getDisplayName());
    } else {
      buffer.writeVarInt(packetPlayerInfo.getPlayerInformation().size());

      for (PlayerInfo info : packetPlayerInfo.getPlayerInformation()) {
        writeAction(
            buffer,
            action,
            info.getName(),
            info.getProfile(),
            info.getGameMode(),
            info.getPing(),
            info.getDisplayName());
      }
    }

    return buffer;
  }

  @Override
  public PacketStrategy getStrategy() {
    return null;
  }

  public PacketBuffer writeAction(
      PacketBuffer buffer,
      PlayerInfoAction action,
      String name,
      PlayerProfile profile,
      GameMode gameMode,
      long ping,
      String displayName) {

    buffer.writeUUID(profile.getUUID());

    switch (action) {
      case ADD_PLAYER:
        {
          buffer.writeUTF8(name);
          if (profile.getProfileStatus().equals(ProfileStatus.GENERATED)) {
            buffer.writeVarInt(0);
          } else {
            buffer.writeVarInt(profile.getSkin().getSignature() == null ? 3 : 4);
            buffer.writeUTF8(profile.getSkin().getName());
            buffer.writeUTF8(profile.getSkin().getValue());
            buffer.writeBoolean(true);
            if (profile.getSkin().getSignature() != null) {
              buffer.writeUTF8(profile.getSkin().getSignature());
            }
          }

          buffer.writeVarInt(gameMode.getIndex());
          buffer.writeVarInt(Math.toIntExact(ping));

          buffer.writeBoolean(displayName != null);
          if (displayName != null) {
            buffer.writeUTF8(displayName);
          }

          break;
        }

      case REMOVE_PLAYER:
        {
          // Nothing happens here.
          break;
        }

      case UPDATE_LATENCY:
        {
          buffer.writeVarInt(Math.toIntExact(ping));
          break;
        }

      case UPDATE_GAMEMODE:
        {
          buffer.writeVarInt(gameMode.getIndex());
          break;
        }

      case UPDATE_DISPLAY_NAME:
        {
          buffer.writeBoolean(displayName != null);
          if (displayName != null) {
            buffer.writeUTF8(displayName);
          }
          break;
        }
    }

    return buffer;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerInfo packetPlayerInfo) {}

  public PlayerInfoAction getAction() {
    return action;
  }

  public String getName() {
    return name;
  }

  public PlayerProfile getProfile() {
    return profile;
  }

  public GameMode getGameMode() {
    return gameMode;
  }

  public long getPing() {
    return ping;
  }

  public String getDisplayName() {
    return displayName;
  }

  public List<PlayerInfo> getPlayerInformation() {
    return playerInformation;
  }

  public enum PlayerInfoAction {
    ADD_PLAYER(0),
    UPDATE_GAMEMODE(1),
    UPDATE_LATENCY(2),
    UPDATE_DISPLAY_NAME(3),
    REMOVE_PLAYER(4);

    private final int index;

    PlayerInfoAction(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }

  public static class PlayerInfo {

    private String name;
    private PlayerProfile profile;
    private GameMode gameMode;
    private long ping;
    private String displayName;

    public PlayerInfo(
        String name, PlayerProfile profile, GameMode gameMode, long ping, String displayName) {

      this.name = name;
      this.profile = profile;
      this.gameMode = gameMode;
      this.ping = ping;
      this.displayName = displayName;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public PlayerProfile getProfile() {
      return profile;
    }

    public void setProfile(PlayerProfile profile) {
      this.profile = profile;
    }

    public GameMode getGameMode() {
      return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
      this.gameMode = gameMode;
    }

    public long getPing() {
      return ping;
    }

    public void setPing(long ping) {
      this.ping = ping;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }
  }
}
