package com.github.avalon.block;

/**
 * Block face is based on table from <url>www.wiki.vg</url>
 *
 * <table>
 *  <tbody>
 *  <tr>
 *    <th>Value</th>
 *    <th>Offset</th>
 *    <th>Face</th>
 *  </tr>
 *  <tr>
 *    <td>0</td>
 *    <td>-Y</td>
 *    <td>Bottom</td>
 *  </tr>
 *  <tr>
 *    <td>1</td>
 *    <td>+Y</td>
 *    <td>Top</td>
 *  </tr>
 *  <tr>
 *    <td>2</td>
 *    <td>-Z</td>
 *    <td>North</td>
 *  </tr>
 *  <tr>
 *    <td>3</td>
 *    <td>+Z</td>
 *    <td>South</td>
 *  </tr>
 *  <tr>
 *    <td>4</td>
 *    <td>-X</td>
 *    <td>West</td>
 *  </tr>
 *  <tr>
 *    <td>5</td>
 *    <td>+X</td>
 *    <td>East</td>
 *  </tr>
 * </tbody>
 * </table>
 */
public enum BlockFace {
  BOTTOM(0, -1, 0),
  TOP(0, +1, 0),
  NORTH(0, 0, -1),
  SOUTH(0, 0, +1),
  WEST(-1, 0, 0),
  EAST(+1, 0, 0);

  private final int xOffset;
  private final int yOffset;
  private final int zOffset;

  BlockFace(int xOffset, int yOffset, int zOffset) {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.zOffset = zOffset;
  }

  public int getXOffset() {
    return xOffset;
  }

  public int getYOffset() {
    return yOffset;
  }

  public int getZOffset() {
    return zOffset;
  }
}
