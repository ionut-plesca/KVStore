package com.db.KVStore;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

public class KVStoreLogPersistentTest {

	@Test
	public void testPut(){
		User user = new User(1L, "Gigi");
		KVStore<User> kvStore = newKVStore();

		kvStore.put("gigi", user);
//		for (long i = 0; i < 100; i++) {
//			kvStore.put("gigi " + i, new User(i, "Gigi " + i));
//		}

		KVStore<User> kvStore2 = newKVStore();
		assertEquals(user, kvStore2.get("gigi"));
	}

	private KVStoreLogPersistent<User> newKVStore() {
		return new KVStoreLogPersistent<>(new KVStoreInMemory<>(new LinkedHashMap<>()), "log.txt", new TypeReference<KeyValue<User>>() {
		});
	}
	
	@Test
	public void testRemove(){
		User user = new User(1L, "Gigi");
		KVStore<User> kvStore = newKVStore();
		kvStore.put("gigi", user);
		kvStore.remove("gigi");
		assertNull(kvStore.get("gigi"));
	}

}
