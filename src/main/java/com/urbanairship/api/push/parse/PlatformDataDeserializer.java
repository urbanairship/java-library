package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.common.parse.APIParsingException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import java.io.IOException;
import java.util.Set;

public class PlatformDataDeserializer extends JsonDeserializer<PlatformData> {

    public static final PlatformDataDeserializer INSTANCE = new PlatformDataDeserializer();

    public PlatformDataDeserializer() { }

    @Override
    public PlatformData deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        PlatformData.Builder builder = PlatformData.newBuilder();

        JsonToken token = parser.getCurrentToken();
        if (token == JsonToken.VALUE_STRING) {
            String value = parser.getText();
            if (!"all".equals(value)) {
                APIParsingException.raise(String.format("Invalid value \"%s\" for \"device_types\". Must be \"all\" or an array of platform identifiers.",
                                                        value),
                                          parser);
            } else {
                builder.setAll(true);
            }
        } else {
            Set<Platform> platforms = parser.readValueAs(new TypeReference<Set<Platform>>() {});
            builder.addAllPlatforms(platforms);
        }
        return builder.build();
    }
}
