package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Options;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class OptionsPayloadReader implements JsonObjectReader<Options> {

    private Options.Builder builder = Options.newBuilder();
    private CropDeserializer cropDS = new CropDeserializer();

    public Options validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readTime(JsonParser parser) throws IOException {
        builder.setTime(parser.getIntValue());
    }

    public void readCrop(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setCrop(cropDS.deserialize(parser, context));
    }
}
