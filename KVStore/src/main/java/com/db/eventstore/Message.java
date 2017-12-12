package com.db.eventstore;

public class Message<T> {
	private final long ts;
	private final String key;
	private final T value;

	public Message(long ts, String key, T value) {
		super();
		this.ts = ts;
		this.key = key;
		this.value = value;
	}

	public long getTs() {
		return ts;
	}

	public String getKey() {
		return key;
	}

	public T getValue() {
		return value;
	}

	

}
