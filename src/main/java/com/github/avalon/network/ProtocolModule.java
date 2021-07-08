package com.github.avalon.network;

import com.github.avalon.module.ServerModule;
import com.github.avalon.server.IServer;

public class ProtocolModule extends ServerModule {

    //TODO: Overhaul whole system of packet to work on annotations and remove protocol status enum and rework it to protocol type.

    public ProtocolModule(IServer host) {
        super(host);
    }
}
