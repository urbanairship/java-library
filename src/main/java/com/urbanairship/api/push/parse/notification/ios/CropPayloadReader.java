package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Crop;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;


public class CropPayloadReader implements JsonObjectReader<Crop> {

    private Crop.Builder builder = Crop.newBuilder();

    public CropPayloadReader(){
    }

    public Crop validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readX(JsonParser parser) throws IOException {
        builder.setX(parser.getFloatValue());
    }

    public void readY(JsonParser parser) throws IOException {
        builder.setY(parser.getFloatValue());
    }

    public void readWidth(JsonParser parser) throws IOException {
        builder.setWidth(parser.getFloatValue());
    }

    public void readHeight(JsonParser parser) throws IOException {
        builder.setHeight(parser.getFloatValue());
    }
}
