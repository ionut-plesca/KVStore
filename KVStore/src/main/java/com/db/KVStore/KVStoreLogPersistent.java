package com.db.KVStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KVStoreLogPersistent<T> extends AbstractKVStoreDecorator<T> {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final TypeReference<KeyValue<T>> typeReference;
	private final Path path;

	public KVStoreLogPersistent(KVStore<T> inner, String fileName, TypeReference<KeyValue<T>> typeReference) {
		super(inner);
		this.path = Paths.get(fileName);
		this.typeReference = typeReference;

		loadFromFile();
		compactFile();

	}

	private void compactFile() {
		try {
			if (Files.exists(path)) {
				Files.delete(path);
				super.stream().forEach(kv -> appendKeyValue(kv.getKey(), kv.getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void put(String key, T value) {
		appendKeyValue(key, value);
		super.put(key, value);

	}

	@Override
	public void remove(String key) {
		appendKeyValue(key, null);
		super.remove(key);
	}

	private void loadFromFile() {

		if (Files.exists(path)) {
			try (Stream<String> lines = Files.lines(path)) {
				lines.map(line -> mapLineToKeyValue(this.typeReference, line)).forEach(kv -> {
					if (kv.getValue() == null) {
						super.remove(kv.getKey());
					} else {
						super.put(kv.getKey(), kv.getValue());
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	private KeyValue<T> mapLineToKeyValue(TypeReference<KeyValue<T>> typeReference, String line) {
		try {
			return objectMapper.readValue(line, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void appendKeyValue(String key, T value) {
		try {
			Files.write(path, Arrays.asList(mapKeyValueToLine(key, value)), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private String mapKeyValueToLine(String key, T value) throws JsonProcessingException {
		return objectMapper.writeValueAsString(new KeyValue<T>(key, value));
	}

}
