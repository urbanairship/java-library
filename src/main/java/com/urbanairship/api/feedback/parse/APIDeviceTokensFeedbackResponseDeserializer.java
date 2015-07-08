/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.feedback.model.APIDeviceTokensFeedbackResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class APIDeviceTokensFeedbackResponseDeserializer extends JsonDeserializer<APIDeviceTokensFeedbackResponse>
{
    private static final FieldParserRegistry<APIDeviceTokensFeedbackResponse, APIDeviceTokensFeedbackResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIDeviceTokensFeedbackResponse, APIDeviceTokensFeedbackResponseReader>(
                    ImmutableMap.<String, FieldParser<APIDeviceTokensFeedbackResponseReader>>builder()
                            .put("device_token", new FieldParser<APIDeviceTokensFeedbackResponseReader>(){
                                @Override
                                public void parse(APIDeviceTokensFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readDeviceToken(jsonParser);
                                }
                            })
                            .put("marked_inactive_on", new FieldParser<APIDeviceTokensFeedbackResponseReader>() {
                                @Override
                                public void parse(APIDeviceTokensFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readMarkedInactiveOn(jsonParser);
                                }

                            })
                            .put("alias", new FieldParser<APIDeviceTokensFeedbackResponseReader>() {
                                @Override
                                public void parse(APIDeviceTokensFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readAlias(jsonParser);
                                }

                            }).build()
            );

    private final StandardObjectDeserializer<APIDeviceTokensFeedbackResponse, ?> deserializer;

    public APIDeviceTokensFeedbackResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APIDeviceTokensFeedbackResponse, APIDeviceTokensFeedbackResponseReader>(
                FIELD_PARSER,
                new Supplier<APIDeviceTokensFeedbackResponseReader>() {
                    @Override
                    public APIDeviceTokensFeedbackResponseReader get() {
                        return new APIDeviceTokensFeedbackResponseReader();
                    }
                }
        );
    }

    @Override
    public APIDeviceTokensFeedbackResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
