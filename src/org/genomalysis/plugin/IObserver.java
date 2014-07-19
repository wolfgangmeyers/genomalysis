package org.genomalysis.plugin;

public abstract interface IObserver extends IErrorListener
{
  public abstract void update();
}