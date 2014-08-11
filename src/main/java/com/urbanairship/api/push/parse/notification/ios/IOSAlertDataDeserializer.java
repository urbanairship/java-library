package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import java.io.IOException;

public class IOSAlertDataDeserializer extends JsonDeserializer<IOSAlertData> {

    private static final FieldParserRegistry<IOSAlertData, IOSAlertDataReader> FIELD_PARSERS = new MapFieldParserRegistry<IOSAlertData, IOSAlertDataReader>(
            ImmutableMap.<String, FieldParser<IOSAlertDataReader>>builder()
            .put("body", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBody(json, context);
                    }
                })
            .put("action-loc-key", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readActionLocKey(json, context);
                    }
                })
            .put("loc-key", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLocKey(json, context);
                    }
                })
            .put("loc-args", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLocArgs(json, context);
                    }
                })
            .put("launch-image", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLaunchImage(json, context);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<IOSAlertData, ?> deserializer;

    public IOSAlertDataDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSAlertData, IOSAlertDataReader>(
            FIELD_PARSERS,
            new Supplier<IOSAlertDataReader>() {
                @Override
                public IOSAlertDataReader get() {
                    return new IOSAlertDataReader();
                }
            }
        );
    }

    @Override
    public IOSAlertData deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            JsonToken token = parser.getCurrentToken();
            switch (token) {

              case VALUE_STRING:
                  String alert = parser.getText();
                  return IOSAlertData.newBuilder()
                      .setBody(alert)
                      .build();

              case START_OBJECT:
                  return deserializer.deserialize(parser, context);

              default:
                  APIParsingException.raise(String.format("Unexpected alert token '%s'", token.name()), parser);
            }
        }
        catch ( APIParsingException e ) {
            throw e;
        } catch ( Exception e ) {
            APIParsingException.raise(e.getMessage(), parser);
        }
        return null;
    }
}
