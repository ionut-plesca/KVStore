package com.db.KVStore;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class KVStoreInMemoryTest {

	private LinkedHashMap<String, User> map;
	private KVStore<User> kvStore;
	public final static String KEY = "Gigi";

	@Before
	public void before() {
		map = new LinkedHashMap<>();
		kvStore = new KVStoreInMemory<>(map);
	}

	@Test
	public void testPut() {

		User user = anUser();
		map.put(KEY, user);

		kvStore.put("Gigi", user);
		assertEquals(user, map.get(KEY));
	}

	@Test
	public void testGet() {
		User user = anUser();
		map.put("Gigi", user);
		assertEquals(user, kvStore.get(KEY));
	}

	@Test
	public void testRemove() {
		User user = new User(1L, "Gigi");
		map.put("Gigi", user);
		assertEquals(user, kvStore.get("Gigi"));
		kvStore.remove("Gigi");
		assertNull(kvStore.get("Gigi"));
	}

	@Test
	public void testStream() {
		User user = anUser();
		map.put(KEY, user);
		assertEquals(Arrays.asList(new KeyValue<>(KEY, user)), kvStore.stream().collect(Collectors.toList()));
	}

	private User anUser() {
		return new User(1L, "Gigi");
	}
}
