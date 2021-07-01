package com.github.avalon.network;

import com.github.avalon.manager.ServerManager;
import com.github.avalon.server.IServer;

public class ProtocolManager extends ServerManager {

    //TODO: Overhaul whole system of packet to work on annotations and remove protocol status enum and rework it to protocol type.

    public ProtocolManager(IServer host) {
        super(host);
    }
}
