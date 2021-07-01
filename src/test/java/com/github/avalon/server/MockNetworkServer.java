package com.github.avalon.server;

import com.github.avalon.chat.ChatManager;
import com.github.avalon.concurrent.ConcurrentManager;
import com.github.avalon.network.SocketServer;
import com.github.avalon.network.PlayerSessionContainer;
import com.github.avalon.network.protocol.ProtocolContainer;
import com.github.avalon.packet.PacketManager;
import com.github.avalon.scheduler.SchedulerManager;

import java.net.InetSocketAddress;

public class MockNetworkServer implements IServer{

    @Override
    public void startServer() {

    }

    @Override
    public void stopServer() {

    }

    @Override
    public void bindServer() {

    }

    @Override
    public ServerState getServerState() {
        return null;
    }

    @Override
    public void setServerState(ServerState serverState) {

    }

    @Override
    public void setServerData(ServerData serverData) {

    }

    @Override
    public ServerData getServerData() {
        return null;
    }

    @Override
    public ProtocolContainer getProtocolContainer() {
        return null;
    }

    @Override
    public PlayerSessionContainer getPlayerSessionRegistry() {
        return null;
    }

    @Override
    public ConcurrentManager getConcurrentManager() {
        return null;
    }

    @Override
    public SchedulerManager getSchedulerManager() {
        return null;
    }

    @Override
    public Bootstrap getServerManager() {
        return null;
    }

    @Override
    public PacketManager getPacketManager() {
        return null;
    }

    @Override
    public ChatManager getChatManager() {
        return null;
    }

    @Override
    public SocketServer getSocketServer() {
        return null;
    }

    @Override
    public InetSocketAddress createAddress(String address, int port) {
        return null;
    }
}
