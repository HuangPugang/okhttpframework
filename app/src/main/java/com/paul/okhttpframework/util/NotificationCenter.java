package com.paul.okhttpframework.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

public class NotificationCenter {
	private HashMap<String, DummyObservable> observables;
	private static NotificationCenter instance = null;
	public static NotificationCenter defaultCenter() {
		if (instance == null) {
			instance = new NotificationCenter();
		}
		return instance;
	}

	public NotificationCenter() {
		observables = new HashMap<String, DummyObservable>();
	}

	public void addObserver(String notification, Observer observer) {
		DummyObservable observable = observables.get(notification);
		if (observable == null) {
			observable = new DummyObservable(notification);
			observables.put(notification, observable);
		}
		observable.addObserver(observer);
	}
	
	public void removeObserver(String notification, Observer observer) {
		DummyObservable observable = observables.get(notification);
		if (observable != null) {
			observable.removeObserver(observer);
		}
	}

	public void postNotification(String notification, Object object) {
		DummyObservable observable = observables.get(notification);
		if (observable != null) {
			if (object == null) {
				observable.notifyObservers();
			} else {
				observable.notifyObservers(object);
			}
		}
	}

	class DummyObservable {
		private String _name;
		private List<Observer> _list = new ArrayList<Observer>();

		DummyObservable(String name) {
			_name = name;
		}
		public void addObserver(Observer observer) {
			_list.add(observer);
		}
		public void removeObserver(Observer observer) {
			_list.remove(observer);
		}
		public void notifyObservers(Object object) {
			List<Observer> iterList = new ArrayList<Observer>(_list);
			for(Observer item : iterList) {
				if (item == null) {
					_list.remove(item);
					Log.i("NotificationCenter", _name + ":WeakReference deleted by GC");
				} else {
					item.update(null, object);
				}
			}
		}
		public void notifyObservers() {
			notifyObservers(null);
		}
	}
}