package com.db.eventstore;

import java.util.stream.IntStream;

import org.junit.Test;

import com.db.KVStore.User;

public class EventStoreTest {
	// private Queue<Mesaj<User>> queue;
	// private Map<String, KVStoreObserver<User>> map;
	//
	// @Before
	// public void before(){
	//// queue = (u-> );
	// map = new LinkedHashMap<>();
	// }
	//
	@Test
	public void testEventStore() {
		EventStore<User> eventStore = new EventStoreInMemory<>();

		eventStore.subscribe("gigi", new RoundRobinConsumer<>(mesaj -> {
			System.out.println("Gigi a primit mesajul");
			System.out.println(mesaj.getValue());
		}, mesaj -> {
			System.out.println("Ion a primit mesajul");
			System.out.println(mesaj.getValue());
		}));
		
		IntStream.range(0, 5).forEach(i-> { eventStore.send(new Message<User>(1L,"gigi"+i, new User(1L,"Gigi"+i)));});

		eventStore.send(new Message<User>(1L, "gigi", new User(1L, "Gigi1")));

		eventStore.unsubscribe("ion");
		eventStore.send(new Message<User>(1L, "gigi", new User(1L, "Gigi2")));

		eventStore.stream().forEach(mesaj -> {
			System.out.println("Mesaj din istoric");
			System.out.println(mesaj.getKey() + " " + mesaj.getValue());
		});
	}

}
