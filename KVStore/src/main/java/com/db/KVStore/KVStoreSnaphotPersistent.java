package com.db.KVStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KVStoreSnaphotPersistent<T> extends AbstractKVStoreDecorator<T> {

	private final String fileName;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Class<?> kvClass;

	public KVStoreSnaphotPersistent(KVStore<T> inner, String fileName, Class<? extends KeyValue<T>> kvClass) {
		super(inner);
		this.fileName = fileName;
		this.kvClass = kvClass;

		if (Files.exists(Paths.get(fileName))) {
			try {
				Files.readAllLines(Paths.get(fileName)).stream().map(this::readKeyValueFromJsonLine).forEach(kv -> {
					super.put(kv.getKey(), kv.getValue());
				});
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private KeyValue<T> readKeyValueFromJsonLine(String line) {
		try {
			return (KeyValue<T>) objectMapper.readValue(line, kvClass);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void put(String key, T value) {
		super.put(key, value);

		List<String> list = super.stream().map(kv -> {
			try {
				return objectMapper.writeValueAsString(kv);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());

		try {
			Files.write(Paths.get(fileName), list, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
