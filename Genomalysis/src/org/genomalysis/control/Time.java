package org.genomalysis.control;

import java.io.PrintStream;
import java.util.Calendar;

public class Time
{
  private int second;
  private int minute;
  private int hour;

  public Time()
  {
    Calendar calendar = Calendar.getInstance();
    this.second = calendar.get(13);
    this.minute = calendar.get(12);
    this.hour = calendar.get(10);
  }

  public Time subtract(Time time) {
    int mySeconds = getTotalSeconds();
    int theirSeconds = time.getTotalSeconds();
    int newSeconds = mySeconds - theirSeconds;
    if (newSeconds < 0)
      newSeconds *= -1;
    return fromSeconds(newSeconds);
  }

  public static Time fromSeconds(int seconds) {
    Time time = new Time();
    if (seconds >= 86400)
      seconds = 86399;
    if (seconds < 0)
      throw new IllegalArgumentException("Cannot create negative time: " + seconds);
    int hour = seconds / 3600;
    int minute = (seconds - hour * 60 * 60) / 60;
    int second = seconds - hour * 60 * 60 + minute * 60;
    time.setHour(hour);
    time.setMinute(minute);
    time.setSecond(second);
    return time;
  }

  public int getTotalSeconds() {
    return (this.second + this.minute * 60 + this.hour * 3600);
  }

  public static void main(String[] args)
  {
    Time time = new Time();

    int seconds = time.getTotalSeconds();
    Time time2 = fromSeconds(seconds);

    System.out.println(time);
    System.out.println(time2);
  }

  public String toString()
  {
    return pad(this.hour) + ":" + pad(this.minute) + ":" + pad(this.second);
  }

  private String pad(int digit)
  {
    return pad(String.valueOf(digit));
  }

  private String pad(String digit) {
    while (digit.length() < 2)
      digit = "0" + digit;
    return digit;
  }

  public int getSecond() {
    return this.second;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  public int getMinute() {
    return this.minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public int getHour() {
    return this.hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }
}