package com.github.avalon.server;

import com.github.avalon.chat.ChatModule;
import com.github.avalon.concurrent.ConcurrentModule;
import com.github.avalon.network.SocketServer;
import com.github.avalon.network.PlayerSessionContainer;
import com.github.avalon.network.protocol.ProtocolContainer;
import com.github.avalon.packet.PacketModule;
import com.github.avalon.scheduler.SchedulerModule;

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
    public ConcurrentModule getConcurrentModule() {
        return null;
    }

    @Override
    public SchedulerModule getSchedulerModule() {
        return null;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public PacketModule getPacketModule() {
        return null;
    }

    @Override
    public ChatModule getChatModule() {
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
