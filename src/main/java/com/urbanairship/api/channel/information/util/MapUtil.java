package com.urbanairship.api.channel.information.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Map;
import java.util.Set;

public class MapUtil {

    public static ImmutableMap<String, ImmutableSet<String>> immutableMapConverter(Map<String, Set<String>> map) {
        ImmutableMap.Builder<String, ImmutableSet<String>> builder = ImmutableMap.builder();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            builder.put(entry.getKey(), ImmutableSet.copyOf(entry.getValue()));
        }
        return builder.build();
    }

    public static void appendMapValues(String key, Set<String> values, Map<String, Set<String>> map) {
        if (!map.containsKey(key)) {
            map.put(key, values);
        } else {
            Set<String> newSet = map.get(key);
            newSet.addAll(values);
            map.put(key, newSet);
        }
    }
}
