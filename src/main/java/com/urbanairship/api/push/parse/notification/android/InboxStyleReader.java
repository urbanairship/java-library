/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.android.InboxStyle;

import java.io.IOException;
import java.util.List;

public class InboxStyleReader implements JsonObjectReader<InboxStyle> {

    private final InboxStyle.Builder builder;

    public InboxStyleReader() {
        this.builder = InboxStyle.newBuilder();
    }

    public void readSummary(JsonParser parser) throws IOException {
        builder.setSummary(StringFieldDeserializer.INSTANCE.deserialize(parser, "summary"));
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readContent(JsonParser parser) throws IOException {
        builder.addLines((List<String>) parser.readValueAs(new TypeReference<List<String>>() {
        }));
    }

    @Override
    public InboxStyle validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
