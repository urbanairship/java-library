/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import java.util.Map;
import java.util.Optional;

public class MapFieldParserRegistry<T, R extends JsonObjectReader<T>> implements FieldParserRegistry<T, R> {

    private final Map<String, FieldParser<R>> registryMap;
    private final Optional<FieldParser<R>> defaultParser;

    public MapFieldParserRegistry(Map<String, FieldParser<R>> registryMap) {
        this(registryMap, null);
    }

    public MapFieldParserRegistry(Map<String, FieldParser<R>> registryMap, FieldParser<R> defaultParser) {
        this.registryMap = registryMap;
        this.defaultParser = Optional.ofNullable(defaultParser);
    }

    @Override
    public Optional<FieldParser<R>> getFieldParser(String fieldName) {
        FieldParser<R> parser = registryMap.get(fieldName);
        if (parser == null && defaultParser.isPresent()) {
            return defaultParser;
        }

        return Optional.ofNullable(registryMap.get(fieldName));
    }
}
