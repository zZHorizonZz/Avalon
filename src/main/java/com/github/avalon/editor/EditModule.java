package com.github.avalon.editor;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.editor.command.BasicCommand;
import com.github.avalon.editor.command.EditCommand;
import com.github.avalon.editor.tools.EditorSession;
import com.github.avalon.editor.tools.basic.Operation;
import com.github.avalon.module.ServerModule;
import com.github.avalon.player.IPlayer;
import com.github.avalon.server.IServer;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.HashMap;
import java.util.Map;

@Module(asynchronous = false, name = "Server Module")
public class EditModule extends ServerModule {

  private final OperationExecutor operationExecutor;
  private final Map<String, EditorSession> sessions;

  public EditModule(IServer host) {
    super(host);

    sessions = new HashMap<>();

    NetworkTaskExecutor taskExecutor = new NetworkTaskExecutor();
    taskExecutor.initialize(new DefaultThreadFactory("Editor Thread"));
    operationExecutor = new OperationExecutor(taskExecutor);

    getHost().getChatModule().registerCommands(new BasicCommand(this));
    getHost().getChatModule().registerCommands(new EditCommand(this));
  }

  public EditorSession getSession(IPlayer player) {
    EditorSession session =
        sessions.getOrDefault(player.getPlayerProfile().getName(), new EditorSession(player));

    if (!sessions.containsKey(player.getPlayerProfile().getName())) {
      sessions.put(player.getPlayerProfile().getName(), session);
    }

    return session;
  }

  public void removeSession(IPlayer player) {}

  public void submitOperation(Operation operation) {
    operationExecutor.processOperation(operation);
  }

  public Map<String, EditorSession> getSessions() {
    return sessions;
  }

  public OperationExecutor getOperationExecutor() {
    return operationExecutor;
  }
}
