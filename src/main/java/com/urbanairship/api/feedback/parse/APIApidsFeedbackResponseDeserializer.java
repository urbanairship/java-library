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
import com.urbanairship.api.feedback.model.APIApidsFeedbackResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class APIApidsFeedbackResponseDeserializer extends JsonDeserializer<APIApidsFeedbackResponse>
{
    private static final FieldParserRegistry<APIApidsFeedbackResponse, APIApidsFeedbackResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIApidsFeedbackResponse, APIApidsFeedbackResponseReader>(
                    ImmutableMap.<String, FieldParser<APIApidsFeedbackResponseReader>>builder()
                    .put("apid", new FieldParser<APIApidsFeedbackResponseReader>(){
                        @Override
                        public void parse(APIApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                        {
                            reader.readApid(jsonParser);
                        }
                    })
                    .put("gcm_registration_id", new FieldParser<APIApidsFeedbackResponseReader>()
                    {
                        @Override
                        public void parse(APIApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                        {
                            reader.readGcmRegistrationId(jsonParser);
                        }

                    })
                    .put("marked_inactive_on", new FieldParser<APIApidsFeedbackResponseReader>() {
                         @Override
                         public void parse(APIApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                         {
                             reader.readMarkedInactiveOn(jsonParser);
                         }

                    })
                    .put("alias", new FieldParser<APIApidsFeedbackResponseReader>() {
                        @Override
                        public void parse(APIApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                        {
                            reader.readAlias(jsonParser);
                        }

                    }).build()
            );

    private final StandardObjectDeserializer<APIApidsFeedbackResponse, ?> deserializer;

    public APIApidsFeedbackResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APIApidsFeedbackResponse, APIApidsFeedbackResponseReader>(
                FIELD_PARSER,
                new Supplier<APIApidsFeedbackResponseReader>() {
                    @Override
                    public APIApidsFeedbackResponseReader get() {
                        return new APIApidsFeedbackResponseReader();
                    }
                }
        );
    }

    @Override
    public APIApidsFeedbackResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
