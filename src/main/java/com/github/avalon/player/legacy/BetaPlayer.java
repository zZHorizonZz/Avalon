package com.github.avalon.player.legacy;

@Deprecated
public class BetaPlayer {

  /*public static int INVALID_MOVES = 10; // This is something like basic antiteleport ac.

  private final IPlayerSession session;
  private final PlayerProfile playerProfile;
  private final NetworkServer server;

  private volatile Status playerStatus = Status.DISCONNECTED;

  private CharacterLiving controllingCharacter;

  private Dimension dimension;
  private volatile List<Chunk> renderedChunks;

  private PlayerSettings settings;
  private GameMode gameMode = GameMode.CREATIVE;
  private GameMode previousGameMode;

  private boolean invulnerable;
  private boolean flying = true;
  private boolean allowedFlying = true;

  private float flyingSpeed = 0.05f;
  private float fieldOfViewModifier = 0.1f;

  private volatile List<Transform> incomingMovements;
  private volatile Transform currentLocation;
  private volatile int locationVerifier;

  private int currentInvalidMoves;

  public BetaPlayer(PlayerProfile playerProfile, IPlayerSession session) {
    this.playerProfile = playerProfile;
    this.session = session;
    this.server = session.getServer();
    this.dimension = getServer().getDimensionManager().getMainDimension();
    this.renderedChunks = new ArrayList<>();

    if (this.dimension == null) {
      session.getConnection().disconnect("Something went wrong with your destination dimension.");
      return;
    }

    this.gameMode = GameMode.CREATIVE;
    this.previousGameMode = GameMode.UNKNOWN;
    this.settings = new PlayerSettings();
    this.incomingMovements = new LinkedList<>();
  }

  public PlayerProfile getPlayerProfile() {
    return playerProfile;
  }

  @Override
  public NetworkServer getServer() {
    return this.server;
  }

  @Override
  public PlayerSettings getSettings() {
    return this.settings;
  }

  @Override
  public void setSettings(PlayerSettings settings) {}

  @Override
  public IPlayerSession getSession() {
    return this.session;
  }

  @Override
  public Status getPlayerStatus() {
    return null;
  }

  @Override
  public void setPlayerStatus(Status status) {}

  @Override
  public Dimension getDimension() {
    return this.dimension;
  }

  @Override
  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  @Override
  public List<Chunk> getRenderingChunks() {
    return null;
  }

  @Override
  public void renderChunks() {
    Chunk currentChunk = getPosition().getChunk();
    if (!renderedChunks.contains(currentChunk)) {
      PacketChunkData data =
          new PacketChunkData(currentChunk.getX(), currentChunk.getZ(), true, currentChunk);
      getSession().sendPacket(data);
      this.renderedChunks.add(currentChunk);
    }
  }

  @Override
  public Chunk getCurrentChunk() {
    return null;
  }

  @Override
  public CharacterLiving getControllingCharacter() {
    return controllingCharacter;
  }

  @Override
  public void setControllingCharacter(CharacterLiving character) {
    this.controllingCharacter = character;
  }

  @Override
  public GameMode getGameMode() {
    return this.gameMode;
  }

  @Override
  public void setGameMode(GameMode gameMode) {
    this.previousGameMode = this.gameMode;
    this.gameMode = gameMode;
  }

  @Override
  public GameMode getPreviousGameMode() {
    return this.previousGameMode;
  }

  @Override
  public void setPreviousGameMode(GameMode gameMode) {
    this.previousGameMode = gameMode;
  }

  @Override
  public boolean isFlying() {
    return this.flying;
  }

  @Override
  public void setFlying(boolean flying) {
    this.flying = flying;
  }

  @Override
  public boolean hasAllowedFlying() {
    return this.allowedFlying;
  }

  @Override
  public void setAllowedFlying(boolean allowedFlying) {
    this.allowedFlying = allowedFlying;
  }

  @Override
  public void setInvulnerable(boolean invulnerable) {
    this.invulnerable = invulnerable;
  }

  @Override
  public boolean isInvulnerable() {
    return this.invulnerable;
  }

  @Override
  public float getFlyingSpeed() {
    return this.flyingSpeed;
  }

  @Override
  public void setFlyingSpeed(float speed) {
    this.flyingSpeed = speed;
  }

  @Override
  public float getFieldOfViewModifier() {
    return this.fieldOfViewModifier;
  }

  @Override
  public void setFieldOfViewModifier(float modifier) {
    this.fieldOfViewModifier = modifier;
  }

  @Override
  public int getViewDistance() {
    return 5;
  }

  @Override
  public void teleport(Transform destination) {
    // TODO Some validation checks for idiots.

    PacketPositionAndLook packet = new PacketPositionAndLook(destination);
    session.sendPacket(packet);

    this.currentLocation = destination;
    this.locationVerifier = packet.getTeleportId();
  }

  @Override
  public Transform getPosition() {
    return this.currentLocation;
  }

  @Override
  public void setViewDistance(int viewDistance) {}

  @Override
  public void connect() {
    new PacketCustomPayload("minecraft:brand", null);
    this.currentLocation = new Transform(getDimension(), 0, 1, 0);

    // TODO: move this function to the PacketBuffer. And addPlayer more packets for reading of the
    // data.

    session.setOnline(true);
    ConsoleManager.logInfo("%s successfully connected.", this.playerProfile.getName());
  }

  @Override
  public void tick() {
    System.out.println("Status: " + this.playerStatus.name());
    if (this.playerStatus == Status.ONLINE) {
      if (!incomingMovements.isEmpty()) {
        Transform lastMove = incomingMovements.get(incomingMovements.size() - 1);

        this.currentLocation = currentLocation.subtract(lastMove);
        getControllingCharacter().move(this.currentLocation);
        teleport(this.currentLocation);

        incomingMovements.clear();
      }

      renderChunks();
    }
  }

  // TODO Add chunk data sending because it appears client can not move in unloaded chunk.
  // TODO Refactor the networkplayer/player to network session, merge it and create more classes for
  // handling of specific actions.

  @Override
  public void handleMovement(PacketPlayerPositionAndRotation packet) {
    double x = packet.getX();
    double y = packet.getY();
    double z = packet.getZ();
    float yaw = packet.getYaw();
    float pitch = packet.getPitch();

    Transform moveTransform = new Transform(getDimension(), x, y, z, yaw, pitch);
    incomingMovements.add(moveTransform);
  }

  @Override
  public void handleChat() {}*/
}
