package com.github.avalon.initialization;

import com.github.avalon.annotation.annotation.Manager;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.manager.AbstractManager;
import com.github.avalon.server.IServer;
import com.github.avalon.timer.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InitializationManager {

  public static final DefaultLogger LOGGER = new DefaultLogger(InitializationManager.class);

  private final Map<String, AbstractManager<?>> managers;
  private final IServer server;

  public InitializationManager(IServer server) {
    managers = new HashMap<>();
    this.server = server;
  }

  public <T extends AbstractManager<?>> T registerManager(T manager) {
    Manager managerAnnotation = manager.getClass().getAnnotation(Manager.class);

    Objects.requireNonNull(
        managerAnnotation,
        () ->
            "Manager annotation is not assigned to this class "
                + manager.getClass().getSimpleName());

    assert !managers.containsKey(managerAnnotation.name())
        : "Manager with this name is already registered.";

    String managerName = managerAnnotation.name();
    Timer managerTimer = new Timer("Startup of " + managerName, false);
    managerTimer.start();

    LOGGER.info("Loading manager class %s...", managerName);

    manager.setSchedulerManager(server.getSchedulerManager());
    manager.setManagerName(managerName);
    server.getConcurrentManager().assignTaskExecutor(manager);
    managers.put(manager.getManagerName(), manager);

    manager.enable();

    managerTimer.stop();
    LOGGER.info("Manager class has been enabled in %s", (double) (managerTimer.elapsed() / 1000));

    return manager;
  }

  public void shutdown() {
    managers.forEach(
        (name, abstractManager) -> {
          abstractManager.disable();
          if (abstractManager.getTaskExecutor() != null) {
            abstractManager.getTaskExecutor().interrupt();
          }
        });
  }

  public AbstractManager<?> getManager(String managerName) {
    return managers.get(managerName);
  }

  public Map<String, AbstractManager<?>> getManagers() {
    return managers;
  }
}
