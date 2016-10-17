/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.IntFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Crop;
import com.urbanairship.api.push.model.notification.ios.IOSMediaOptions;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class IOSMediaOptionsPayloadReader implements JsonObjectReader<IOSMediaOptions> {

    private final IOSMediaOptions.Builder builder;

    public IOSMediaOptionsPayloadReader() {
        this.builder = IOSMediaOptions.newBuilder();
    }

    @Override
    public IOSMediaOptions validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readTime(JsonParser parser) throws IOException {
        builder.setTime(IntFieldDeserializer.INSTANCE.deserialize(parser, "time"));
    }

    public void readCrop(JsonParser parser) throws IOException {
        builder.setCrop(parser.readValueAs(Crop.class));
    }

    public void readHidden(JsonParser parser) throws IOException {
        builder.setHidden(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "hidden"));
    }
}
