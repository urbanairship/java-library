/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.LocationAlias;
import com.urbanairship.api.segments.model.LocationIdentifier;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class LocationIdentifierDeserializer extends JsonDeserializer<LocationIdentifier> {

    public static final LocationIdentifierDeserializer INSTANCE = new LocationIdentifierDeserializer();

    private static final String INVALID_LOCATION = "Location constraints must contain a payload with a location identifier and an optional date range specifier";
    private static final String INVALID_LOCATION_IDENTIFIER_VALUE = "Location identifier values must be strings or numbers";
    private static final String INVALID_LOCATION_IDENTIFIER_VALUE_BLANK = "Location identifier values cannot be blank";

    private LocationIdentifierDeserializer() {
    }

    @Override
    public LocationIdentifier deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token != JsonToken.FIELD_NAME) {
            throw new InvalidAudienceSegmentException(INVALID_LOCATION);
        }

        String identifierType = jp.getText();
        token = jp.nextToken();
        if (!isValidLocationIdentifierValueType(token)) {
            throw new InvalidAudienceSegmentException(INVALID_LOCATION_IDENTIFIER_VALUE);
        }

        String value = jp.getText();
        if (StringUtils.isBlank(value)) {
            throw new InvalidAudienceSegmentException(INVALID_LOCATION_IDENTIFIER_VALUE_BLANK);
        }

        if ("id".equals(identifierType)) {
            return new LocationIdentifier(value);
        }

        return new LocationIdentifier(LocationAlias.newBuilder()
                .setAliasType(identifierType)
                .setAliasValue(value)
                .build()
        );
    }

    private boolean isValidLocationIdentifierValueType(JsonToken token) {
        return token == JsonToken.VALUE_STRING || token == JsonToken.VALUE_NUMBER_INT;
    }
}
