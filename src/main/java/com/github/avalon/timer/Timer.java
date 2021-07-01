package com.github.avalon.timer;

import com.github.avalon.common.system.UtilTime;
import com.github.avalon.console.logging.DefaultLogger;

/**
 * Timer class is class used for checking the times for examples execution time of some operation.
 *
 * @author Horizon
 * @version 1.0
 */
public class Timer {

  public static final DefaultLogger LOGGER = new DefaultLogger(Timer.class);

  private String name = "Default Timer";

  private long start;
  private long end;

  private boolean deepLog = true;

  public Timer() {}

  public Timer(String name) {
    this.name = name;
  }

  public Timer(String name, boolean deepLog) {
    this.name = name;
    this.deepLog = deepLog;
  }

  public void start() {
    start = System.currentTimeMillis();

    if (deepLog) {
      LOGGER.info(
          "[Timer] %s has been started on time %s", name, UtilTime.convertTimeWithTimeZome(start));
    }
  }

  public long stop() {
    end = System.currentTimeMillis();

    if (deepLog) {
      LOGGER.info(
          "[Timer] %s has been stopped on time %s", name, UtilTime.convertTimeWithTimeZome(start));
      LOGGER.info("[Timer] %s timestamp of timer is %s milliseconds.", end - start);
    }

    return end - start;
  }

  public long elapsed() {
    return end - start;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(long end) {
    this.end = end;
  }
}
