package com.github.avalon.account.handler;

import com.github.avalon.account.PlayerProfile;
import com.github.avalon.account.ProfileCallback;
import com.github.avalon.account.ProfileStatus;
import com.github.avalon.common.system.UtilSecurity;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.http.HttpClient;
import com.github.avalon.packet.PacketListener;
import com.github.avalon.packet.annotation.PacketHandler;
import com.github.avalon.packet.packet.login.PacketEncryptionKeyRequest;
import com.github.avalon.packet.packet.login.PacketEncryptionKeyResponse;
import com.github.avalon.packet.packet.login.PacketLoginIn;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.server.NetworkServer;

import javax.annotation.Nullable;
import javax.crypto.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Handles the {@link PacketEncryptionKeyResponse} that is sent by client. This packet is sent only
 * when we verifying client via Mojang servers for originality of his account. And also handles
 * {@link PacketLoginIn} that client sends as a first packet after switch to LOGIN protocol.
 *
 * @version 1.0
 */
public class LoginHandler implements PacketListener {

  public static final DefaultLogger LOGGER = new DefaultLogger(LoginHandler.class);
  public static final String BASE_URL =
      "https://sessionserver.mojang.com/session/minecraft/hasJoined";

  private final HttpClient httpClient;

  public LoginHandler() {
    httpClient = new HttpClient();
  }

  @PacketHandler
  public void handleEncryptionKeyResponse(
      PacketEncryptionKeyResponse packet, PlayerConnection connection) {
    IPlayer player = connection.getPlayer();
    String hash =
        createHashAndEnableEncryption(player, packet.getSharedSecret(), packet.getVerifyToken());
    String url = BASE_URL + "?username=" + player.getVerifyUsername() + "&serverId=" + hash;

    httpClient.connect(url, connection.getChannel().eventLoop(), new ProfileCallback(player));
  }

  @PacketHandler
  public void handleLoginIn(PacketLoginIn packet, PlayerConnection connection) {
    IPlayer player = connection.getPlayer();

    String name = packet.getUsername();
    NetworkServer server = player.getServer();

    if (!server.getServerData().isDeveloperMode()) {
      String sessionId = connection.getSessionId();
      byte[] publicKey = new byte[0];

      try {
        publicKey = UtilSecurity.generateX509Key(server.getSecurityKey().getPublic()).getEncoded();
      } catch (InvalidKeySpecException | NoSuchAlgorithmException exception) {
        LOGGER.error("Public key generation error.", exception);
        player.disconnect("Public key generation error. Please try it again later.");
      }

      byte[] verifyToken = UtilSecurity.generateVerifyToken();

      player.setVerifyToken(verifyToken);
      player.setVerifyUsername(name);

      connection.send(new PacketEncryptionKeyRequest(sessionId, publicKey, verifyToken));
    } else {
      UUID uuid = UUID.nameUUIDFromBytes(("Generated:" + name).getBytes(StandardCharsets.UTF_8));
      PlayerProfile profile = new PlayerProfile(name, uuid, ProfileStatus.GENERATED);

      player.getActionHandler().handleLogin(profile);
    }
  }

  /**
   * Generates hash from inserted keys and session.
   *
   * @since 1.0
   * @param sessionIdentifier Identifier of current player's session.
   * @param sharedSecretKey Shared secret key from packet.
   * @param publicSecretKey Secret key that is used by server.
   * @return Generated hash.
   * @throws NoSuchAlgorithmException Because of usage of {@link MessageDigest} if right algorithm
   *     is not found then exception is thrown. (Should not happen.)
   */
  private String generateHash(
      byte[] sessionIdentifier, byte[] sharedSecretKey, byte[] publicSecretKey)
      throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-1");
    digest.update(sessionIdentifier);
    digest.update(sharedSecretKey);
    digest.update(publicSecretKey);
    return new BigInteger(digest.digest()).toString(16);
  }

  /**
   * Creates secure hash for verification of client's session on mojang servers.
   *
   * @since 1.0
   * @param player Player that is currently authenticated. (Will be kicked if something goes wrong)
   * @param sharedSecretKey Shared secret key that is sent by client.
   * @param playersVerifyToken Player's verification token.
   * @return Substring of generated hash for verification.
   */
  @Nullable
  private String createHashAndEnableEncryption(
      IPlayer player, byte[] sharedSecretKey, byte[] playersVerifyToken) {
    SecretKey sharedSecret;
    byte[] verifyToken;

    try {
      Cipher rsaCipher = UtilSecurity.generateRsaCipher();
      sharedSecret = UtilSecurity.generateSecretKey(rsaCipher, sharedSecretKey);
      verifyToken = UtilSecurity.generateVerifyToken(rsaCipher, playersVerifyToken);
    } catch (NoSuchPaddingException | NoSuchAlgorithmException exception) {
      LOGGER.error("General security exception occurred.", exception);
      player.disconnect("General security exception occurred. Please try it again later.");
      return null;
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException exception) {
      LOGGER.error("Decryption of secret hash or verification token failed.", exception);
      player.disconnect(
          "Decryption of secret or verification token hash failed. Please try it again later.");
      return null;
    }

    if (!Arrays.equals(verifyToken, player.getVerifyToken())) {
      player.disconnect(
          "Something went wrong and verification token is not valid. Please try it again later.");
      return null;
    }

    player.getConnection().enableEncryption(sharedSecret);

    String hash;
    try {
      hash =
          generateHash(
              player.getConnection().getSessionId().getBytes(StandardCharsets.UTF_8),
              sharedSecret.getEncoded(),
              player.getServer().getSecurityKey().getPublic().getEncoded());
    } catch (NoSuchAlgorithmException exception) {
      LOGGER.error("Hashing of player's verification crypt failed.", exception);
      player.disconnect("Hashing of verification crypt failed. Please try it again later.");
      return null;
    }

    return hash;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }
}
