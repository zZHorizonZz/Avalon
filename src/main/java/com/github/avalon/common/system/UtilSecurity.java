package com.github.avalon.common.system;

import com.github.avalon.server.Bootstrap;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * This utility contains mostly generation/creation of secret key and tokens.
 *
 * @version 1.0
 */
public class UtilSecurity {

  private static final SecureRandom random = new SecureRandom();

  private UtilSecurity() {}

  /**
   * Generate a RSA key pair.
   *
   * @return The RSA key pair.
   */
  public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(1024);

    KeyPair keyPair = generator.generateKeyPair();
    return keyPair;
  }

  /**
   * Generate a random verify token.
   *
   * @return An array of 4 random bytes.
   */
  public static byte[] generateVerifyToken() {
    byte[] token = new byte[4];
    random.nextBytes(token);
    return token;
  }

  /**
   * Generates an X509 formatted key used in authentication.
   *
   * @param base The key to use to generate a public key from its key spec.
   * @return The X509 formatted key.
   */
  public static Key generateX509Key(Key base)
      throws InvalidKeySpecException, NoSuchAlgorithmException {
    X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(base.getEncoded());
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    Key key = keyFactory.generatePublic(encodedKeySpec);

    return key;
  }

  public static Cipher generateRsaCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
    return Cipher.getInstance("RSA");
  }

  public static SecretKey generateSecretKey(Cipher cipher, byte[] sharedSecretKey)
      throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    cipher.init(Cipher.DECRYPT_MODE, Bootstrap.INSTANCE.getServer().getSecurityKey().getPrivate());
    return new SecretKeySpec(cipher.doFinal(sharedSecretKey), "AES");
  }

  public static byte[] generateVerifyToken(Cipher cipher, byte[] verifyToken)
      throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    cipher.init(Cipher.DECRYPT_MODE, Bootstrap.INSTANCE.getServer().getSecurityKey().getPrivate());
    return cipher.doFinal(verifyToken);
  }

  public static int generateVerifyIdentifier() {
    return random.nextInt(Integer.MAX_VALUE);
  }
}
