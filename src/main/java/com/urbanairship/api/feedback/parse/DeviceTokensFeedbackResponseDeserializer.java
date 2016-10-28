/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.feedback.model.DeviceTokensFeedbackResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class DeviceTokensFeedbackResponseDeserializer extends JsonDeserializer<DeviceTokensFeedbackResponse>
{
    private static final FieldParserRegistry<DeviceTokensFeedbackResponse, DeviceTokensFeedbackResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<DeviceTokensFeedbackResponse, DeviceTokensFeedbackResponseReader>(
                    ImmutableMap.<String, FieldParser<DeviceTokensFeedbackResponseReader>>builder()
                            .put("device_token", new FieldParser<DeviceTokensFeedbackResponseReader>(){
                                @Override
                                public void parse(DeviceTokensFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readDeviceToken(jsonParser);
                                }
                            })
                            .put("marked_inactive_on", new FieldParser<DeviceTokensFeedbackResponseReader>() {
                                @Override
                                public void parse(DeviceTokensFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readMarkedInactiveOn(jsonParser);
                                }

                            })
                            .put("alias", new FieldParser<DeviceTokensFeedbackResponseReader>() {
                                @Override
                                public void parse(DeviceTokensFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readAlias(jsonParser);
                                }

                            }).build()
            );

    private final StandardObjectDeserializer<DeviceTokensFeedbackResponse, ?> deserializer;

    public DeviceTokensFeedbackResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<DeviceTokensFeedbackResponse, DeviceTokensFeedbackResponseReader>(
                FIELD_PARSER,
                new Supplier<DeviceTokensFeedbackResponseReader>() {
                    @Override
                    public DeviceTokensFeedbackResponseReader get() {
                        return new DeviceTokensFeedbackResponseReader();
                    }
                }
        );
    }

    @Override
    public DeviceTokensFeedbackResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
