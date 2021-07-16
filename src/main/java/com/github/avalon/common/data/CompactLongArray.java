package com.github.avalon.common.data;

public class CompactLongArray {

  private int entriesPerLong;

  private int[] data;

  public CompactLongArray(int entriesPerLong) {
    this.entriesPerLong = entriesPerLong;
  }

  public long[] toArray(int[] data) {
    this.data = data;

    long[] array = new long[data.length / entriesPerLong];
    long entry = 0;
    int index = 0;
    int arrayIndex = 0;

    // System.out.println("Compact data: " + maximumEntries + " Per long: " + entriesPerLong + "
    // Data: " + Arrays.toString(data));
    for (int datum : data) {
      if (index == 0) {
        entry |= datum;
        index++;
      } else if (index + 1 == entriesPerLong) {
        entry |= (long) datum << (0x05 * index);
        array[arrayIndex++] = entry;
        entry = 0;
        index = 0;
      } else {
        entry |= (long) datum << (0x05 * index);
        index++;
      }
    }

    return array;
  }

  public int[] getData() {
    return data;
  }

  public int getEntriesPerLong() {
    return entriesPerLong;
  }
}
