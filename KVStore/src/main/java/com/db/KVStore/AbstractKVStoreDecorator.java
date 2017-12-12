package com.db.KVStore;

import java.util.stream.Stream;

public abstract class AbstractKVStoreDecorator<T> implements KVStore<T> {
	private final KVStore<T> inner;

	public AbstractKVStoreDecorator(KVStore<T> inner) {
		super();
		this.inner = inner;
	}

	public void put(String key, T value) {
		inner.put(key, value);
	}

	public T get(String key) {
		return inner.get(key);
	}

	public void remove(String key) {
		inner.remove(key);
	}

	public Stream<KeyValue<T>> stream() {
		return inner.stream();
	}

}
