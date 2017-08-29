/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;

import java.io.IOException;

public class BigTextStyleReader implements JsonObjectReader<BigTextStyle> {

    private final BigTextStyle.Builder builder;

    public BigTextStyleReader() {
        this.builder = BigTextStyle.newBuilder();
    }

    public void readSummary(JsonParser parser) throws IOException {
        builder.setSummary(StringFieldDeserializer.INSTANCE.deserialize(parser, "summary"));
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readContent(JsonParser parser) throws IOException {
        builder.setContent(StringFieldDeserializer.INSTANCE.deserialize(parser, "big_text"));
    }

    @Override
    public BigTextStyle validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
