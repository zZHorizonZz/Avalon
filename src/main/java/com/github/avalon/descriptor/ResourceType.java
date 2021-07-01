package com.github.avalon.descriptor;

public enum ResourceType {

    MINECRAFT("minecraft"),
    NONE(null);

    private final String resourceName;

    ResourceType(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
