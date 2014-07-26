package org.genomalysis.control;

import java.util.ArrayList;
import java.util.List;

public class EventSupport implements IObservable {

	private List<IObserver> observers = new ArrayList<IObserver>();
	
	public void addObserver(IObserver observer) {
		synchronized (observers) {
			if (!observers.contains(observer)) {
				observers.add(observer);
			}
		}
	}

	public void removeObserver(IObserver observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	public void notifyObserversOfError(String errorMessage) {
		synchronized (observers) {
			for (IObserver observer : observers) {
				observer.showError(errorMessage);
			}
		}
	}

	public void notifyObservers() {
		synchronized (observers) {
			for (IObserver observer : observers) {
				observer.update();
			}
		}
	}
}
