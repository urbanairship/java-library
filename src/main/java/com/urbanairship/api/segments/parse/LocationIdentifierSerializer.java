/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.LocationIdentifier;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class LocationIdentifierSerializer extends JsonSerializer<LocationIdentifier> {

    public static final LocationIdentifierSerializer INSTANCE = new LocationIdentifierSerializer();

    private LocationIdentifierSerializer() {
    }

    @Override
    public void serialize(LocationIdentifier value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        String fieldName = value.isAlias() ? value.getAlias().getAliasType() : "id";
        String valueString = value.isAlias() ? value.getAlias().getAliasValue() : value.getId();

        jgen.writeFieldName(fieldName);
        jgen.writeString(valueString);
    }
}
