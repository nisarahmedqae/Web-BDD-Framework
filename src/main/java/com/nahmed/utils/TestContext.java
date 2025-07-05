package com.nahmed.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestContext {

    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public <T> T getData(String key, Class<T> type) {
        Object value = data.get(key);

        // If the type is already correct, just cast and return
        if (type.isInstance(value)) {
            return type.cast(value);
        }

        // --- Automatic Type Conversion ---
        if (value instanceof String) {
            String stringValue = (String) value;
            try {
                if (type == Integer.class) {
                    return type.cast(Integer.valueOf(stringValue));
                }
                if (type == Boolean.class) {
                    return type.cast(Boolean.valueOf(stringValue));
                }
                if (type == Long.class) {
                    return type.cast(Long.valueOf(stringValue));
                }
                if (type == Double.class) {
                    return type.cast(Double.valueOf(stringValue));
                }
            } catch (NumberFormatException e) {
                throw new ClassCastException(String.format(
                        "Failed to convert String value '%s' to type %s for key '%s'.",
                        stringValue, type.getSimpleName(), key
                ));
            }
        }

        // If no conversion is possible, throw a clear exception.
        throw new ClassCastException(String.format(
                "Error retrieving data. Value for key '%s' is of type %s, but you requested %s. No automatic conversion was possible.",
                key,
                value.getClass().getName(),
                type.getName()
        ));
    }

    public void setData(String key, Object value) {
        data.put(key, value);
    }

}