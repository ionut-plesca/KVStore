package com.db.KVStore;

import java.util.stream.Stream;

public interface KVStore<V> {
	void put(String key, V value);

	V get(String key);

	public void remove(String key);

	Stream<KeyValue<V>> stream();

}
