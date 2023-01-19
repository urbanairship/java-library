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
            .put("body", (reader, json, context) -> reader.readBody(json))
            .put("action-loc-key", (reader, json, context) -> reader.readActionLocKey(json))
            .put("loc-key", (reader, json, context) -> reader.readLocKey(json))
            .put("loc-args", (reader, json, context) -> reader.readLocArgs(json))
            .put("launch-image", (reader, json, context) -> reader.readLaunchImage(json))
            .put("summary-arg", (reader, json, context) -> reader.readSummaryArg(json))
            .put("summary-arg-count", (reader, json, context) -> reader.readSummaryArgCount(json))
            .put("title", (reader, json, context) -> reader.readTitle(json))
            .put("title-loc-args", (reader, json, context) -> reader.readTitleLocArgs(json))
            .put("title-loc-key", (reader, json, context) -> reader.readTitleLocKey(json))
            .put("subtitle-loc-args", (reader, json, context) -> reader.readSubtitleLocArgs(json))
            .put("subtitle-loc-key", (reader, json, context) -> reader.readSubtitleLocKey(json))
            .build()
            );

    private final StandardObjectDeserializer<IOSAlertData, ?> deserializer;

    public IOSAlertDataDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSAlertData, IOSAlertDataReader>(
            FIELD_PARSERS,
                () -> new IOSAlertDataReader()
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
