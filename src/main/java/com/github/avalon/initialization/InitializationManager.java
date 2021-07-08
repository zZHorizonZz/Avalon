package com.github.avalon.initialization;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.module.AbstractModule;
import com.github.avalon.server.IServer;
import com.github.avalon.timer.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InitializationManager {

  public static final DefaultLogger LOGGER = new DefaultLogger(InitializationManager.class);

  private final Map<String, AbstractModule<?>> modules;
  private final IServer server;

  public InitializationManager(IServer server) {
    modules = new HashMap<>();
    this.server = server;
  }

  public <T extends AbstractModule<?>> T registerModule(T manager) {
    Module moduleAnnotation = manager.getClass().getAnnotation(Module.class);

    Objects.requireNonNull(
            moduleAnnotation,
        () ->
            "Module annotation is not assigned to this class "
                + manager.getClass().getSimpleName());

    assert !modules.containsKey(moduleAnnotation.name())
        : "Module with this name is already registered.";

    String managerName = moduleAnnotation.name();
    Timer managerTimer = new Timer("Startup of " + managerName, false);
    managerTimer.start();

    LOGGER.info("Loading manager class %s...", managerName);

    manager.setSchedulerManager(server.getSchedulerModule());
    manager.setManagerName(managerName);
    server.getConcurrentModule().assignTaskExecutor(manager);
    modules.put(manager.getManagerName(), manager);

    manager.enable();

    managerTimer.stop();
    LOGGER.info("Module class has been enabled in %s", (double) (managerTimer.elapsed() / 1000));

    return manager;
  }

  public void shutdown() {
    modules.forEach(
        (name, abstractManager) -> {
          abstractManager.disable();
          if (abstractManager.getTaskExecutor() != null) {
            abstractManager.getTaskExecutor().interrupt();
          }
        });
  }

  public AbstractModule<?> getModule(String managerName) {
    return modules.get(managerName);
  }

  public Map<String, AbstractModule<?>> getModules() {
    return modules;
  }
}
