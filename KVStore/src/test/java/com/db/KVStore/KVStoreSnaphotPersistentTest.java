package com.db.KVStore;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Test;

public class KVStoreSnaphotPersistentTest {

	@Test
	public void testPut() {

		User user = new User(1L, "Gigi");
		KVStore<User> kvStore = newKVStore();

		kvStore.put("gigi", user);
		for (long i = 0; i < 100; i++) {
			kvStore.put("gigi " + i, new User(i, "Gigi " + i));
		}

		KVStore<User> kvStore2 = newKVStore();
		assertEquals(user, kvStore2.get("gigi"));
	}

	private KVStoreSnaphotPersistent<User> newKVStore() {
		return new KVStoreSnaphotPersistent<>(new KVStoreInMemory<>(new LinkedHashMap<>()), "users.txt", UserKV.class);
	}

	private static final class UserKV extends KeyValue<User> {
	}

}
