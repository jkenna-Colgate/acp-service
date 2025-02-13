package com.acp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValueManagerService {

    private final Map<String, String> keyValueStore = new HashMap<>();

    public String writeValue(String key, String value) {
        if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
            return "Invalid input: Key and value cannot be empty";
        }
        keyValueStore.put(key, value);
        return "Value stored successfully";
    }

    public String deleteValue(String key) {
        if (keyValueStore.containsKey(key)) {
            keyValueStore.remove(key);
            return "Key deleted successfully";
        } else {
            return "Key not found";
        }
    }

    public String readValue(String key) {
        return keyValueStore.getOrDefault(key, "Key not found");
    }

    public Map<String, String> getAllValues() {
        return keyValueStore;
    }
}