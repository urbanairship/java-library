/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.LocationAlias;
import com.urbanairship.api.push.model.audience.location.LocationIdentifier;
import com.urbanairship.api.push.model.audience.location.LocationSelector;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class LocationSelectorReader implements JsonObjectReader<LocationSelector> {

    private final LocationSelector.Builder builder;

    public LocationSelectorReader() {
        this.builder = LocationSelector.newBuilder();
    }

    public void readId(JsonParser parser) throws IOException {
        builder.setId(LocationIdentifier.newBuilder()
                      .setId(parser.getText())
                      .build());
    }

    public void readDateRange(JsonParser parser) throws IOException {
        builder.setDateRange(parser.readValueAs(DateRange.class));
    }

    public void readAlias(JsonParser parser) throws IOException {
        String aliasType = parser.getCurrentName();
        String aliasValue = parser.getText();
        builder.setId(LocationIdentifier.newBuilder()
                      .setAlias(LocationAlias.newBuilder()
                                .setType(aliasType)
                                .setValue(aliasValue)
                                .build())
                      .build());
    }

    @Override
    public LocationSelector validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
