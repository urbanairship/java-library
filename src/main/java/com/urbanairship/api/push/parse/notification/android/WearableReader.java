/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.android.Wearable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WearableReader implements JsonObjectReader<Wearable> {
    private final Wearable.Builder builder;

    public WearableReader() {
        this.builder = Wearable.newBuilder();
    }

    public void readBackgroundImage(JsonParser parser) throws IOException {
        builder.setBackgroundImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "background_image"));
    }

    public void readInteractive(JsonParser parser) throws IOException {
        builder.setInteractive(parser.readValueAs(Interactive.class));
    }

    public void readExtraPages(JsonParser parser) throws IOException {
        List<Map<String,String>> mutablePages = parser.readValueAs(new TypeReference<List<Map<String,String>>>() {
        });
        builder.addAllExtraPages(immutableMapsConverter(mutablePages));
    }

    @Override
    public Wearable validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    private static ImmutableList<ImmutableMap<String, String>> immutableMapsConverter(List<Map<String, String>> maps) {
        ImmutableList.Builder<ImmutableMap<String,String>> pages = ImmutableList.builder();
        for (Map<String,String> map : maps) {
            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.put(entry.getKey(), entry.getValue());
            }
            pages.add(builder.build());
        }
        return pages.build();
    }
}
