/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
public final class LocationDeserializer extends JsonDeserializer<APIErrorDetails.Location> {

    private static final FieldParserRegistry<APIErrorDetails.Location, LocationReader> FIELD_PARSERS =
            new MapFieldParserRegistry<APIErrorDetails.Location, LocationReader>(
                    ImmutableMap.<String, FieldParser<LocationReader>>builder()
                    .put("line", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readLine(jsonParser);
                        }
                    })
                    .put("column", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readColumn(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<APIErrorDetails.Location, ?> deserializer;

    // See Google Guava for Supplier details
    public LocationDeserializer(){
        deserializer = new StandardObjectDeserializer<APIErrorDetails.Location, LocationReader>(
                FIELD_PARSERS,
                new Supplier<LocationReader>(){
                    @Override
                    public LocationReader get(){
                        return new LocationReader();
                    }
                }
        );
    }

    @Override
    public APIErrorDetails.Location deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }


}
