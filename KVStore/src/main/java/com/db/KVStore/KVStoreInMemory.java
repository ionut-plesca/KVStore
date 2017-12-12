package com.db.KVStore;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class KVStoreInMemory<V> implements KVStore<V> {

	private final LinkedHashMap<String, V> store;

	public KVStoreInMemory(LinkedHashMap<String, V> store) {
		super();
		this.store = store;
	}

	@Override
	public void put(String key, V value) {
		store.put(key, value);
	}

	@Override
	public V get(String k) {
		return store.get(k);
	}

	@Override
	public void remove(String k) {
		store.remove(k);
	}

	@Override
	public Stream<KeyValue<V>> stream() {
		return store.entrySet().stream().map(n -> new KeyValue<>(n.getKey(), n.getValue()));

	}


}
