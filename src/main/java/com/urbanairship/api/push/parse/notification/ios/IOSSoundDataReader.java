package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;

import java.io.IOException;

public class IOSSoundDataReader implements JsonObjectReader<IOSSoundData> {

    private IOSSoundData.Builder builder = IOSSoundData.newBuilder();

    public IOSSoundDataReader() { }

    public void readCritical(JsonParser parser) throws IOException {
        builder.setCritical(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "critical"));
    }

    public void readVolume(JsonParser parser) throws IOException {
        builder.setVolume(parser.readValueAs(Double.TYPE));
    }

    public void readName(JsonParser parser) throws IOException {
        builder.setName(StringFieldDeserializer.INSTANCE.deserialize(parser, "name"));
    }

    @Override
    public IOSSoundData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
