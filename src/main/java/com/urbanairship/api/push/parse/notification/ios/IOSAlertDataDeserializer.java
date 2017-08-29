/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;

import java.io.IOException;

public class IOSAlertDataDeserializer extends JsonDeserializer<IOSAlertData> {

    private static final FieldParserRegistry<IOSAlertData, IOSAlertDataReader> FIELD_PARSERS = new MapFieldParserRegistry<IOSAlertData, IOSAlertDataReader>(
            ImmutableMap.<String, FieldParser<IOSAlertDataReader>>builder()
            .put("body", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBody(json);
                    }
                })
            .put("action-loc-key", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readActionLocKey(json);
                    }
                })
            .put("loc-key", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLocKey(json);
                    }
                })
            .put("loc-args", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLocArgs(json);
                    }
                })
            .put("launch-image", new FieldParser<IOSAlertDataReader>() {
                    public void parse(IOSAlertDataReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLaunchImage(json);
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
