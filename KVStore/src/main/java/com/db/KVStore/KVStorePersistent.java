package com.db.KVStore;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class KVStorePersistent<V> implements KVStore<V> {

	private final LinkedHashMap<String, V> map;

	public KVStorePersistent(LinkedHashMap<String, V> map) {
		super();
		this.map = map;
	}

	@Override
	public void put(String key, V value) {
		// TODO Auto-generated method stub

	}

	@Override
	public V get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Stream<KeyValue<V>> stream() {
		// TODO Auto-generated method stub
		return null;
	}

}
