package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.Android;

import java.io.IOException;

public final class DeviceTypeReader implements JsonObjectReader<Android> {

    private final Android.Builder builder;

    public DeviceTypeReader() { this.builder = Android.newBuilder(); }

    public void readDirect(JsonParser jsonParser) throws IOException {
        builder.setDirect(jsonParser.readValueAs(int.class));
    }

    public void readInfluenced(JsonParser jsonParser) throws IOException {
        builder.setInfluenced(jsonParser.readValueAs(int.class));
    }

    @Override
    public Android validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
