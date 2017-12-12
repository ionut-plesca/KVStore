package com.db.KVStore;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class KVStoreObservableTest {

	private User user;
	private KVStore<User> kvStore;
	// private KVStoreInMemory<User> kvStoreInMem;

	@Before
	public void before() {
		user = new User(1L, "Gigi");
		kvStore = kvStore();
		// kvStoreInMem=new KVStoreInMemory<>(new LinkedHashMap<>());
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPut() {
		KVStoreObserver<User> mockObserver = Mockito.mock(KVStoreObserver.class);

		kvStore = new KVStoreObservable<>(new KVStoreInMemory<>(new LinkedHashMap<>()), (k, o, n) -> {
			System.out.println(k + " " + o + " " + n);
		}, mockObserver);
		kvStore.put("gigi", user);
		kvStore.put("gigi", new User(2L, "Gigi"));

		Mockito.verify(mockObserver).onChange("gigi", null, new User(1L, "Gigi"));
		Mockito.verify(mockObserver).onChange("gigi", new User(1L, "Gigi"), new User(2L, "Gigi"));

	}

	private KVStore<User> kvStore() {
		KVStore<User> kvStore = new KVStoreObservable<User>(new KVStoreInMemory<>(new LinkedHashMap<>()),
				(k, o, n) -> System.out.println(k + " " + o + " " + n));
		return kvStore;
	}

	// @Test
	// public void testRemove(){
	// before();
	// kvStore.put("gigi", user);
	// kvStore.remove("gigi");
	// }

}
