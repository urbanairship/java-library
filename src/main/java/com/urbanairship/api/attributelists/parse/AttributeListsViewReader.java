/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.attributelists.model.AttributeListsView;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;

public class AttributeListsViewReader implements JsonObjectReader<AttributeListsView> {
    private final AttributeListsView.Builder builder;

    public AttributeListsViewReader() {
        this.builder = AttributeListsView.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(Boolean.class));
    }

    public void readName(JsonParser jsonParser) throws IOException {
        builder.setName(jsonParser.readValueAs(String.class));
    }

    public void readDescription(JsonParser jsonParser) throws IOException {
        builder.setDescription(jsonParser.readValueAs(String.class));
    }

    public void readExtras(JsonParser jsonParser) throws IOException {
        Map<String, String> mutableExtras = jsonParser.readValueAs(new TypeReference<Map<String, String>>() {});
        ImmutableMap<String, String> extras = immutableMapConverter(mutableExtras);
        builder.addAllExtras(extras);
    }

    public void readCreated(JsonParser jsonParser) throws IOException {
        builder.setCreated(jsonParser.readValueAs(DateTime.class));
    }

    public void readLastUpdated(JsonParser jsonParser) throws IOException {
        builder.setLastUpdated(jsonParser.readValueAs(DateTime.class));
    }

    public void readChannelCount(JsonParser jsonParser) throws IOException {
        builder.setChannelCount(jsonParser.readValueAs(int.class));
    }

    public void readErrorPath(JsonParser jsonParser) throws IOException {
        builder.setErrorPath(jsonParser.readValueAs(String.class));
    }

    public void readStatus(JsonParser jsonParser) throws IOException {
        builder.setStatus(jsonParser.readValueAs(String.class));
    }


    @Override
    public AttributeListsView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

    private static ImmutableMap<String, String> immutableMapConverter(Map<String, String> map) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.put(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
