package com.db.eventstore;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface EventStore<T> {
	void subscribe(String id, Consumer<Message<T>> consumer);
	
	void send (Message<T> message);
	
	Stream<Message<T>> stream();
	
	void unsubscribe(String id);

}
