package com.github.avalon.manager;

import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.scheduler.SchedulerManager;

/**
 * Abstract manager contains all necessary methods, fields, etc for main class of some module. We
 * have currently two types of managers first is Parent Manager that is annotated with {@link
 * com.github.avalon.annotation.annotation.Manager} annotation. Second is child manager that
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
public abstract class AbstractManager<H> {

  public static final DefaultLogger LOGGER = new DefaultLogger(AbstractManager.class);

  private String managerName;
  private H host;
  private Class<? extends AbstractManager<?>>[] dependencies;

  private NetworkTaskExecutor taskExecutor;
  private SchedulerManager schedulerManager;

  public AbstractManager(H host) {
    this(host, null, (Class<? extends AbstractManager<?>>) null);
  }

  public AbstractManager(String managerName, H host) {
    this(host, null, (Class<? extends AbstractManager<?>>) null);

    this.managerName = managerName;
  }

  @SafeVarargs
  public AbstractManager(
          H host, NetworkTaskExecutor executor, Class<? extends AbstractManager<?>>... dependencies) {

    this.host = host;
    taskExecutor = executor;

    if (dependencies != null && dependencies.length != 0) this.dependencies = dependencies;
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

  public Class<? extends AbstractManager<?>>[] getDependencies() {
    return dependencies;
  }

  public void setDependencies(Class<? extends AbstractManager<?>>[] dependencies) {
    this.dependencies = dependencies;
  }

  public NetworkTaskExecutor getTaskExecutor() {
    return taskExecutor;
  }

  public void setTaskExecutor(NetworkTaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  public SchedulerManager getSchedulerManager() {
    return schedulerManager;
  }

  public void setSchedulerManager(SchedulerManager schedulerManager) {
    this.schedulerManager = schedulerManager;
  }
}
