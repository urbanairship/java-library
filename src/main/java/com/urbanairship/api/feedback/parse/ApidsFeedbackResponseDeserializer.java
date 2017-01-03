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
import com.urbanairship.api.feedback.model.ApidsFeedbackResponse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ApidsFeedbackResponseDeserializer extends JsonDeserializer<ApidsFeedbackResponse>
{
    private static final FieldParserRegistry<ApidsFeedbackResponse, ApidsFeedbackResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<ApidsFeedbackResponse, ApidsFeedbackResponseReader>(
                    ImmutableMap.<String, FieldParser<ApidsFeedbackResponseReader>>builder()
                    .put("apid", new FieldParser<ApidsFeedbackResponseReader>(){
                        @Override
                        public void parse(ApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                        {
                            reader.readApid(jsonParser);
                        }
                    })
                    .put("gcm_registration_id", new FieldParser<ApidsFeedbackResponseReader>()
                    {
                        @Override
                        public void parse(ApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                        {
                            reader.readGcmRegistrationId(jsonParser);
                        }

                    })
                    .put("marked_inactive_on", new FieldParser<ApidsFeedbackResponseReader>() {
                         @Override
                         public void parse(ApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                         {
                             reader.readMarkedInactiveOn(jsonParser);
                         }

                    })
                    .put("alias", new FieldParser<ApidsFeedbackResponseReader>() {
                        @Override
                        public void parse(ApidsFeedbackResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
                        {
                            reader.readAlias(jsonParser);
                        }

                    }).build()
            );

    private final StandardObjectDeserializer<ApidsFeedbackResponse, ?> deserializer;

    public ApidsFeedbackResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ApidsFeedbackResponse, ApidsFeedbackResponseReader>(
                FIELD_PARSER,
                new Supplier<ApidsFeedbackResponseReader>() {
                    @Override
                    public ApidsFeedbackResponseReader get() {
                        return new ApidsFeedbackResponseReader();
                    }
                }
        );
    }

    @Override
    public ApidsFeedbackResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
