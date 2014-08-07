package org.genomalysis.control;

public interface IObservable {

    void addObserver(IObserver observer);

    void removeObserver(IObserver observer);
}
