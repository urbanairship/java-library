/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSMediaContent;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class IOSMediaContentReader implements JsonObjectReader<IOSMediaContent> {

    private final IOSMediaContent.Builder builder;

    public IOSMediaContentReader() {
        this.builder = IOSMediaContent.newBuilder();
    }

    @Override
    public IOSMediaContent validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e){
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readTitle(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readSubtitle(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setSubtitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "subtitle"));
    }

    public void readBody(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }
}
