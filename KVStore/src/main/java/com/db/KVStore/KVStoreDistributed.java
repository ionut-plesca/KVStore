package com.db.KVStore;

import java.util.ArrayList;
import java.util.stream.Stream;

public class KVStoreDistributed<T> implements KVStore<T> {
	// private final ArrayList<KVStore<T>> kvStoreList;

	// public KVStoreDistributed(KVStore<T> inner, ArrayList<KVStore<T>>
	// kvStoreList) {
	// super(inner);
	// this.kvStoreList = kvStoreList;
	//
	// }

	private KVStore<T> stores[];

	@SafeVarargs
	public KVStoreDistributed(KVStore<T>... stores) {
		this.stores = stores;
	}

	@Override
	public void put(String key, T value) {
		// super.put(key, value);
		getStore(key).put(key, value);

	}

	private KVStore<T> getStore(String key) {
		return stores[key.hashCode() % stores.length];
	}

	@Override
	public T get(String key) {
		return getStore(key).get(key);

	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		getStore(key).remove(key);

	}

	@Override
	public Stream<KeyValue<T>> stream() {
		return Stream.of(stores).flatMap(store -> store.stream());
		
		
	}

}
