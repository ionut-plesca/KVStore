package com.db.KVStore;

import java.util.stream.Stream;

public class KVStoreObservable<T> extends AbstractKVStoreDecorator<T> {
	private final KVStoreObserver<T>[] observers;

	@SafeVarargs
	public KVStoreObservable(KVStore<T> inner, KVStoreObserver<T>... observers) {
		super(inner);
		this.observers = observers;
	}

	@Override
	public void put(String key, T value) {
		T oldValue = super.get(key);
		super.put(key, value);
		notifyObservers(key, oldValue, value);
	}

	public void notifyObservers(String key, T oldValue, T value) {
		Stream.of(observers).forEach(o -> o.onChange(key, oldValue, value));
	}

	

	@Override
	public void remove(String key) {
		T oldValue = super.get(key);
		super.remove(key);
		notifyObservers(key, oldValue, null);
	}

}
