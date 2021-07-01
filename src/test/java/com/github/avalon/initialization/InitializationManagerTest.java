package com.github.avalon.initialization;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.avalon.manager.AbstractManager;
import com.github.avalon.server.MockNetworkServer;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class InitializationManagerTest {
    @Test
    public void testConstructor() {
        MockNetworkServer mockNetworkServer = new MockNetworkServer();
        Map<String, AbstractManager<?>> managers = (new InitializationManager(mockNetworkServer)).getManagers();
        assertTrue(managers instanceof java.util.HashMap);
        assertTrue(managers.isEmpty());
        assertNull(mockNetworkServer.getConcurrentManager());
        assertNull(mockNetworkServer.getServerState());
        assertNull(mockNetworkServer.getServerData());
        assertNull(mockNetworkServer.getPlayerSessionRegistry());
    }
}

