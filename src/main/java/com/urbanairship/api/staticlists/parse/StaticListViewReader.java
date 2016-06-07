/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.parse;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;

public class StaticListViewReader implements JsonObjectReader<StaticListView> {
    private final StaticListView.Builder builder;

    public StaticListViewReader() {
        this.builder = StaticListView.newBuilder();
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

    public void readStatus(JsonParser jsonParser) throws IOException {
        builder.setStatus(jsonParser.readValueAs(String.class));
    }


    @Override
    public StaticListView validateAndBuild() throws IOException {
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
