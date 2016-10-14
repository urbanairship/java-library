/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.IntFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Crop;
import com.urbanairship.api.push.model.notification.ios.Options;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class OptionsPayloadReader implements JsonObjectReader<Options> {

    private final Options.Builder builder;

    public OptionsPayloadReader() {
        this.builder = Options.newBuilder();
    }

    @Override
    public Options validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readTime(JsonParser parser) throws IOException {
        builder.setTime(IntFieldDeserializer.INSTANCE.deserialize(parser, "time"));
    }

    public void readCrop(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setCrop(parser.readValueAs(Crop.class));
    }
}
