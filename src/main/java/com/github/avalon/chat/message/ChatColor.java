package com.github.avalon.chat.message;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a simple way to manage and create a custom chat colors. This class also
 * provides automatic creation of of the {@link Message} for future handling.
 *
 * @version 1.0
 * @author https://github.com/SpigotMC/BungeeCord with some modifications by Horizon.
 */
public final class ChatColor {

  public static final Map<String, ChatColor> BY_NAME = new HashMap<>();

  public static final ChatColor BLACK = new ChatColor("black", new Color(0x000000));
  public static final ChatColor DARK_BLUE = new ChatColor("dark_blue", new Color(0x0000AA));
  public static final ChatColor DARK_GREEN = new ChatColor("dark_green", new Color(0x00AA00));
  public static final ChatColor DARK_AQUA = new ChatColor("dark_aqua", new Color(0x00AAAA));
  public static final ChatColor DARK_RED = new ChatColor("dark_red", new Color(0xAA0000));
  public static final ChatColor DARK_PURPLE = new ChatColor("dark_purple", new Color(0xAA00AA));
  public static final ChatColor GOLD = new ChatColor("gold", new Color(0xFFAA00));
  public static final ChatColor GRAY = new ChatColor("gray", new Color(0xAAAAAA));
  public static final ChatColor DARK_GRAY = new ChatColor("dark_gray", new Color(0x555555));
  public static final ChatColor BLUE = new ChatColor("blue", new Color(0x05555FF));
  public static final ChatColor GREEN = new ChatColor("green", new Color(0x55FF55));
  public static final ChatColor AQUA = new ChatColor("aqua", new Color(0x55FFFF));
  public static final ChatColor RED = new ChatColor("red", new Color(0xFF5555));
  public static final ChatColor LIGHT_PURPLE = new ChatColor("light_purple", new Color(0xFF55FF));
  public static final ChatColor YELLOW = new ChatColor("yellow", new Color(0xFFFF55));
  public static final ChatColor WHITE = new ChatColor("white", new Color(0xFFFFFF));

  private final String name;
  private final Color color;

  private ChatColor(String name, Color color) {
    this.name = name;
    this.color = color;

    BY_NAME.put(name, this);
  }

  public static ChatColor fromHex(String hex) {
    Color color = Color.decode(hex);
    return new ChatColor(hex, color);
  }

  public String toHex() {
    return '#' + Integer.toHexString(color.getRGB()).substring(2);
  }

  public String getName() {
    return name;
  }

  public Color getColor() {
    return color;
  }
}
