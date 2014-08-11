package com.urbanairship.api.push.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PlatformDeserializer extends JsonDeserializer<Platform> {

    @Override
    public Platform deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String deviceTypeString = jp.getText();
        Optional<Platform> platform = Platform.fromIdentifierFunction.apply(deviceTypeString);
        if (!platform.isPresent()) {
            APIParsingException.raise(String.format("Unrecognized device type '%s'",deviceTypeString), jp);
        }

        return platform.get();
    }
}
