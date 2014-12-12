/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.LocationAlias;
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
        if (value.isAlias()) {
            writeAlias(value.getAlias(), jgen);
        } else {
            jgen.writeFieldName("id");
            jgen.writeString(value.getId());
        }

    }

    private void writeAlias(LocationAlias alias, JsonGenerator jgen) throws IOException {
        jgen.writeFieldName(alias.getAliasType());
        jgen.writeString(alias.getAliasValue());
    }

}
