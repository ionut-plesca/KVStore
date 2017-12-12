package com.db.KVStore;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class KVStoreDistributedTest {
	// public final static int NODES = 2;
	// private final ArrayList<KVStore<User>> kvStoreList =
	// Arrays.asList((KVStore<User>) new KVStoreInMemory<>(new
	// LinkedHashMap<>()),(KVStore<User>)new KVStoreLogPersistent<>(new
	// KVStoreInMemory<>(new LinkedHashMap<>()), "log1.txt", new
	// TypeReference<KeyValue<User>>(){}));
	//
	// @Test
	// public void testPut() {
	// User user = new User(1L, "Gigi");
	// KVStore<User> kvStore = newKVStore();
	//
	// kvStore.put("Gigi", user);
	//
	// boolean eq = kvStoreList.get(0).equals(user) ||
	// kvStoreList.get(1).equals(user);
	//
	// assertEquals(eq, true);
	//
	//
	// }
	//
	// private KVStore<User> newKVStore(){
	// return new KVStoreDistributed<User>(new KVStoreInMemory<>(new
	// LinkedHashMap<>()), new ArrayList<KVStore<User>>());
	// }

	KVStoreInMemory<User> kv1;
	KVStoreInMemory<User> kv2;
	KVStoreInMemory<User> kv3;
	KVStore<User> kvStore;
	User user;

	@Before
	public void before() {
		kv1 = aKVStore();
		kv2 = aKVStore();
		kv3 = aKVStore();
		kvStore = new KVStoreDistributed<>(kv1, kv2, kv3);
		user = new User(1L, "Gigi");
	}

	@Test
	public void testPut() {

		kvStore.put("gigi", user);
		assertNull(kv1.get("gigi"));
		assertNull(kv2.get("gigi"));
		assertEquals(user, kv3.get("gigi"));
	}

	@Test
	public void testGet() {
		kv3.put("gigi", user);
		assertEquals(user, kvStore.get("gigi"));
	}

	@Test
	public void testRemove() {
		kv3.put("gigi", user);
		kvStore.remove("gigi");
		assertNull(kv3.get("gigi"));
	}

	@Test
	public void testStream() {
		kv3.put("gigi", user);
		assertEquals(Arrays.asList(new KeyValue<User>("gigi", user)), kvStore.stream().collect(Collectors.toList()));
		
		kvStore.stream().map(kv->kv.getValue()).filter(u -> u.getId()<100L).forEach(u -> System.out.println(u));
	}

	private KVStoreInMemory<User> aKVStore() {
		return new KVStoreInMemory<>(new LinkedHashMap<>());
	}
}
