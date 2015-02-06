package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import java.io.IOException;

public class PushExpiryDeserializer extends JsonDeserializer<PushExpiry> {

    private static final DateTimeDeserializer DT = new DateTimeDeserializer();

    @Override
    public PushExpiry deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            JsonToken token = parser.getCurrentToken();
            switch (token) {

                case VALUE_STRING:
                    return PushExpiry.newBuilder()
                        .setExpiryTimeStamp(DT.deserialize(parser, context))
                        .build();

                case VALUE_NUMBER_INT:
                    int expiry = parser.getIntValue();
                    return PushExpiry.newBuilder()
                        .setExpirySeconds(expiry)
                        .build();

                default:
                    throw APIParsingException.raise(String.format("Unexpected token '%s' while parsing expiry time", token.name()), parser);
            }
        }
        catch ( APIParsingException e ) {
            throw e;
        } catch ( Exception e ) {
            throw APIParsingException.raise(e.getMessage(), parser);
        }
    }
}
