package com.github.avalon.player.legacy;

@Deprecated
public enum ViewDistance {

    LOW(4),
    MEDIUM(6),
    HIGH(8),
    HIGHEST(10),
    MAXIMUM(12);

    private final int chunkDistance;

    ViewDistance(int chunkDistance) {
        this.chunkDistance = chunkDistance;
    }
}
