package com.github.avalon.module;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.scheduler.SchedulerModule;

/**
 * Abstract module contains all necessary methods, fields, etc for main class of some module. We
 * have currently two types of modules first is Parent module that is annotated with {@link
 * Module} annotation. Second is child module that
 * doesn't have any annotation. If manager has annotation then it will should be registered with
 * {@link com.github.avalon.initialization.InitializationManager} because this manager
 * automatically assign the {@link NetworkTaskExecutor} if manager in it's annotation has {@code
 * asynchronous = true}.
 *
 * @param <H> Type of parent class of manager usually is {@link
 *     com.github.avalon.server.IServer}.
 * @author Horizon
 * @version 1.1
 */
public abstract class AbstractModule<H> {

  public static final DefaultLogger LOGGER = new DefaultLogger(AbstractModule.class);

  private String managerName;
  private H host;
  private Class<? extends AbstractModule<?>>[] dependencies;

  private NetworkTaskExecutor taskExecutor;
  private SchedulerModule schedulerManager;

  protected AbstractModule(H host) {
    this(host, null, (Class<? extends AbstractModule<?>>) null);
  }

  protected AbstractModule(String managerName, H host) {
    this(host, null, (Class<? extends AbstractModule<?>>) null);

    this.managerName = managerName;
  }

  @SafeVarargs
  protected AbstractModule(
          H host, NetworkTaskExecutor executor, Class<? extends AbstractModule<?>>... dependencies) {

    this.host = host;
    taskExecutor = executor;

    if (dependencies != null && dependencies.length != 0) {
      this.dependencies = dependencies;
    }
  }

  /**
   * Method is called by {@link com.github.avalon.initialization.InitializationManager} after
   * managers initialization.
   *
   * @since 1.0
   */
  public void enable() {}

  /**
   * Method is called by {@link com.github.avalon.initialization.InitializationManager} after
   * managers full shutdown.
   *
   * @since 1.0
   */
  public void disable() {}

  public String getManagerName() {
    return managerName;
  }

  public void setManagerName(String managerName) {
    this.managerName = managerName;
  }

  public H getHost() {
    return host;
  }

  public void setHost(H host) {
    this.host = host;
  }

  public Class<? extends AbstractModule<?>>[] getDependencies() {
    return dependencies;
  }

  public void setDependencies(Class<? extends AbstractModule<?>>[] dependencies) {
    this.dependencies = dependencies;
  }

  public NetworkTaskExecutor getTaskExecutor() {
    return taskExecutor;
  }

  public void setTaskExecutor(NetworkTaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  public SchedulerModule getSchedulerManager() {
    return schedulerManager;
  }

  public void setSchedulerManager(SchedulerModule schedulerManager) {
    this.schedulerManager = schedulerManager;
  }
}
