package com.db.eventstore;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EventStoreInMemory<T> implements EventStore<T> {

	private final Queue<Message<T>> queue = new ConcurrentLinkedQueue<Message<T>>();
	private final Map<String, Consumer<Message<T>>> observers = new LinkedHashMap<>();

	@Override
	public void subscribe(String id, Consumer<Message<T>> consumer) {
		observers.put(id, consumer);
	}

	@Override
	public void send(Message<T> message) {
		queue.offer(message);
		observers.values().stream().forEach(o -> {
			o.accept(message);
		});

	}

	@Override
	public Stream<Message<T>> stream() {
		return queue.stream();
	}

	@Override
	public void unsubscribe(String id) {
		observers.remove(id);

	}

}
