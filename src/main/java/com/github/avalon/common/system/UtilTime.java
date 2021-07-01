package com.github.avalon.common.system;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class UtilTime {

  public static TimeZone timeZone = TimeZone.getDefault();

  public static String convertTimeWithTimeZome(long time) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(timeZone);
    cal.setTimeInMillis(time);
    return (cal.get(Calendar.YEAR)
        + " "
        + (cal.get(Calendar.MONTH) + 1)
        + " "
        + cal.get(Calendar.DAY_OF_MONTH)
        + " "
        + cal.get(Calendar.HOUR_OF_DAY)
        + ":"
        + cal.get(Calendar.MINUTE));
  }

  public static String convertString(long time, TimeUnit... types) {
    if (time == -1) return "Permanent";

    final long days = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(time);
    time -= java.util.concurrent.TimeUnit.DAYS.toMillis(days);
    final long hours = java.util.concurrent.TimeUnit.MILLISECONDS.toHours(time);
    time -= java.util.concurrent.TimeUnit.HOURS.toMillis(hours);
    final long minutes = java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(time);
    time -= java.util.concurrent.TimeUnit.MINUTES.toMillis(minutes);
    final long seconds = java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(time);
    time -= java.util.concurrent.TimeUnit.SECONDS.toMillis(seconds);

    final StringBuilder timeString = new StringBuilder();
    for (TimeUnit timeUnit : types) {
      if (days > 0 && timeUnit.equals(TimeUnit.DAYS)) {
        timeString.append(days);
        timeString.append("d ");
      }
      if (hours > 0 && timeUnit.equals(TimeUnit.HOURS)) {
        timeString.append(hours);
        timeString.append("h ");
      }
      if (minutes > 0 && timeUnit.equals(TimeUnit.MINUTES)) {
        timeString.append(minutes);
        timeString.append("m ");
      }
      if (seconds > 0 && timeUnit.equals(TimeUnit.SECONDS)) {
        timeString.append(seconds);
        timeString.append("s ");
      }
    }

    return timeString.toString();
  }
}
