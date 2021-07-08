package com.github.avalon.initialization;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.avalon.module.AbstractModule;
import com.github.avalon.server.MockNetworkServer;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class InitializationModuleTest {
    @Test
    public void testConstructor() {
        MockNetworkServer mockNetworkServer = new MockNetworkServer();
        Map<String, AbstractModule<?>> managers = (new InitializationManager(mockNetworkServer)).getModules();
        assertTrue(managers instanceof java.util.HashMap);
        assertTrue(managers.isEmpty());
        assertNull(mockNetworkServer.getConcurrentModule());
        assertNull(mockNetworkServer.getServerState());
        assertNull(mockNetworkServer.getServerData());
        assertNull(mockNetworkServer.getPlayerSessionRegistry());
    }
}

