package com.github.avalon.common.data;

public class CompactLongArray {

  private int entriesPerLong;
  private int maximumEntries;

  private int[] data;

  public CompactLongArray(int entriesPerLong, int maximumEntries) {
    this.entriesPerLong = entriesPerLong;
    this.maximumEntries = maximumEntries;
  }

  public long[] toArray(int[] data) {
    this.data = data;

    long[] array = new long[maximumEntries];
    long entry = 0;
    int index = 0;
    int arrayIndex = 0;

    for (int datum : data) {
      if (index == 0) {
        entry |= datum;
      } else if (index == entriesPerLong) {
        entry |= (long) datum << 0x05;
        array[arrayIndex++] = entry;
        entry = 0;
      } else {
        entry |= (long) datum << 0x05;
      }

      index++;
    }

    return array;
  }

  public int[] getData() {
    return data;
  }

  public int getEntriesPerLong() {
    return entriesPerLong;
  }

  public int getMaximumEntries() {
    return maximumEntries;
  }
}
