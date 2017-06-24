/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Crop;

import java.io.IOException;


public class CropPayloadReader implements JsonObjectReader<Crop> {

    private final Crop.Builder builder;

    public CropPayloadReader(){
        this.builder = Crop.newBuilder();
    }

    @Override
    public Crop validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readX(JsonParser parser) throws IOException {
        builder.setX(parser.getDecimalValue());
    }

    public void readY(JsonParser parser) throws IOException {
        builder.setY(parser.getDecimalValue());
    }

    public void readWidth(JsonParser parser) throws IOException {
        builder.setWidth(parser.getDecimalValue());
    }

    public void readHeight(JsonParser parser) throws IOException {
        builder.setHeight(parser.getDecimalValue());
    }
}
