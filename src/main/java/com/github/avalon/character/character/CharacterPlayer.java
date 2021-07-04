package com.github.avalon.character.character;

import com.github.avalon.account.PlayerProfile;
import com.github.avalon.data.Transform;
import com.github.avalon.packet.PacketBatch;
import com.github.avalon.packet.packet.play.PacketPlayerInfo;
import com.github.avalon.packet.packet.play.PacketSpawnPlayer;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.attributes.GameMode;

import java.util.Collection;

public class CharacterPlayer extends CharacterLiving {

  private PlayerProfile profile;

  public CharacterPlayer(
      IPlayer controller,
      int identifier,
      Transform transform,
      PacketPlayerInfo.PlayerInfo playerInfo) {
    super(identifier, transform);

    profile = playerInfo.getProfile();

    setController(controller);
    setName(playerInfo.getName());
    setDisplayName(playerInfo.getDisplayName());
  }

  public CharacterPlayer(
      int identifier, Transform transform, PacketPlayerInfo.PlayerInfo playerInfo) {
    super(identifier, transform);
  }

  public CharacterPlayer(
      IPlayer controller,
      int identifier,
      Transform transform,
      String name,
      String displayName,
      PlayerProfile profile) {
    super(identifier, transform);

    this.profile = profile;

    setController(controller);
    setName(name);
    setDisplayName(displayName);
  }

  public CharacterPlayer(
      int identifier, Transform transform, String name, String displayName, PlayerProfile profile) {
    super(identifier, transform);

    this.profile = profile;

    setName(name);
    setDisplayName(displayName);
  }

  @Override
  public void teleport(IPlayer player, Transform transform) {}

  @Override
  public void spawn() {
    assert getTransform() != null : "Valid transform should be set before entity is spawned!";

    Transform transform = getTransform();

    Collection<IPlayer> receivers = transform.getDimension().getPlayers();
    if (getController() != null) {
      receivers.remove(getController());
    }

    PacketPlayerInfo packetPlayerInfo =
        new PacketPlayerInfo(
            getName(),
            profile,
            getController() != null
                ? getController().getPlayerAttributes().getGameMode()
                : GameMode.CREATIVE,
            getController() != null ? 1 : -1,
            getDisplayName());

    PacketSpawnPlayer packetSpawnPlayer =
        new PacketSpawnPlayer(getIdentifier(), getProfile().getUUID(), transform);

    for (IPlayer player : receivers) {
      player.sendPacket(new PacketBatch(packetPlayerInfo, packetSpawnPlayer));
    }
  }

  @Override
  public void synchronize() {
    if (getController() == null) {}
  }

  @Override
  public void attack() {}

  public PlayerProfile getProfile() {
    return profile;
  }

  public void setProfile(PlayerProfile profile) {
    this.profile = profile;
  }

  public enum PlayerStatus {
    FINISH_ACTION((byte) 9),
    ENABLE_REDUCED_DEBUG((byte) 22),
    DISABLE_REDUCED_DEBUG((byte) 23),
    SET_OP_LEVEL1((byte) 24),
    SET_OP_LEVEL2((byte) 25),
    SET_OP_LEVEL3((byte) 26),
    SET_OP_LEVEL4((byte) 27),
    SPAWN_CLOUD_PARTICLE((byte) 43);

    private final byte status;

    PlayerStatus(byte status) {
      this.status = status;
    }

    public byte getStatus() {
      return status;
    }
  }
}
