package com.github.avalon.packet;

import com.github.avalon.common.exception.NotSupportedException;
import com.github.avalon.packet.packet.Packet;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;

/**
 * This class provides an custom {@link Packet} save in {@link Set}. It is used by {@link
 * com.github.avalon.player.IPlayerSession} to send packets in row.
 *
 * @author Horizon
 * @version 1.0
 */
public class PacketBatch implements Set<Packet<?>> {
  private final List<Packet<?>> packetList;

  public PacketBatch(Packet<?>... packets) {
    packetList = new LinkedList<>();
    packetList.addAll(Arrays.asList(packets));
  }

  public PacketBatch() {
    packetList = new LinkedList<>();
  }

  @Override
  public int size() {
    return packetList.size();
  }

  @Override
  public boolean isEmpty() {
    return packetList.isEmpty();
  }

  @Override
  public boolean contains(Object object) {
    return packetList.contains(object);
  }

  @Override
  public Iterator<Packet<?>> iterator() {
    return packetList.iterator();
  }

  @Override
  public Object[] toArray() {
    return packetList.toArray();
  }

  @Override
  public <T> T[] toArray(@Nonnull T[] packetArray) {
    return (T[]) packetList.toArray(new Packet<?>[0]);
  }

  @Override
  public boolean add(Packet<?> packet) {
    return packetList.add(packet);
  }

  @Override
  public boolean remove(Object object) {
    return packetList.remove(object);
  }

  @Override
  public boolean containsAll(@Nonnull Collection<?> collection) {
    throw new NotSupportedException("This method is not supported.");
  }

  @Override
  public boolean addAll(@Nonnull Collection<? extends Packet<?>> collection) {
    throw new NotSupportedException("This method is not supported.");
  }

  @Override
  public boolean retainAll(@Nonnull Collection<?> collection) {
    throw new NotSupportedException("This method is not supported.");
  }

  @Override
  public boolean removeAll(@Nonnull Collection<?> collection) {
    throw new NotSupportedException("This method is not supported.");
  }

  @Override
  public void clear() {
    packetList.clear();
  }

  @Override
  public void forEach(Consumer<? super Packet<?>> action) {
    packetList.forEach(action);
  }
}
