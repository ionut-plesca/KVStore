package com.db.KVStore;

public interface KVStoreObserver<T> {
	void onChange(String k, T oldValue, T newValue);

}
