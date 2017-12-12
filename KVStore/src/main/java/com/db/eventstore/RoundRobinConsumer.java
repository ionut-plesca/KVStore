package com.db.eventstore;

import java.util.function.Consumer;

public class RoundRobinConsumer<T> implements Consumer<Message<T>> {
	
	private final Consumer<Message<T>> consumers[];
	private int index = -1;

	
	
	@SafeVarargs
	public RoundRobinConsumer(Consumer<Message<T>> ... consumers) {
		super();
		this.consumers = consumers;
	}



	@Override
	public void accept(Message<T> message) {
		consumers[index=(index+1)%consumers.length].accept(message);

	}

}
