/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.nameduser.model.NamedUserView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NamedUserViewReader implements JsonObjectReader<NamedUserView> {
    ObjectMapper mapper = NamedUserObjectMapper.getInstance();
    private NamedUserView.Builder builder;

    public NamedUserViewReader() {
        builder = NamedUserView.newBuilder();
    }

    public void readNamedUserId(JsonParser jsonParser) throws IOException {
        builder.setNamedUserId(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, "named_user_id"));
    }

    public void readNamedUserTags(JsonParser jsonParser) throws IOException {
        Map<String, Set<String>> mutableTags = jsonParser.readValueAs(new TypeReference<Map<String, Set<String>>>() {});
        ImmutableMap<String, ImmutableSet<String>> tags = immutableMapConverter(mutableTags);
        builder.setNamedUserTags(tags);
    }

    public void readChannelView(JsonParser jsonParser) throws  IOException {
        Set<ChannelView> channels = jsonParser.readValueAs(new TypeReference<Set<ChannelView>>() {});
        builder.setChannelViews(ImmutableSet.copyOf(channels));
    }

    public void readAttributes(JsonParser jsonParser) throws IOException {
        Map<String, String> result = mapper.readValue(jsonParser, HashMap.class);
        builder.addAllAttributes(result);
    }

    public void readUserAttributes(JsonParser jsonParser) throws IOException {
        Map<String, String> result = mapper.readValue(jsonParser, HashMap.class);
        builder.addAllUserAttributes(result);
    }

    @Override
    public NamedUserView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

    private static ImmutableMap<String, ImmutableSet<String>> immutableMapConverter(Map<String, Set<String>> map) {
        ImmutableMap.Builder<String, ImmutableSet<String>> builder = ImmutableMap.builder();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            builder.put(entry.getKey(), ImmutableSet.copyOf(entry.getValue()));
        }
        return builder.build();
    }

}
