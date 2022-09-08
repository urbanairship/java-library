package com.urbanairship.api.push.model.audience.LocationIdentifierTest;


import com.urbanairship.api.push.model.audience.location.LocationAlias;
import com.urbanairship.api.push.model.audience.location.LocationIdentifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
public class LocationIdentifierTest {

    @Test
    public void testEquals() {
        LocationIdentifier l1 = LocationIdentifier.newBuilder()
                .setId("ID")
                .build();

        LocationIdentifier l2 = LocationIdentifier.newBuilder()
                .setId("ID")
                .build();
        assertEquals("Equals should return true", l1, l2);
        LocationIdentifier l3 = LocationIdentifier.newBuilder()
                .setId("foo")
                .build();

        assertNotEquals("Equals should return false", l1, l3);

        LocationAlias TX = LocationAlias.newBuilder()
                .setType("us_state")
                .setValue("TX")
                .build();
        LocationAlias OR = LocationAlias.newBuilder()
                .setType("us_state")
                .setValue("OR")
                .build();

        LocationIdentifier l4 = LocationIdentifier.newBuilder()
                .setAlias(TX)
                .build();
        LocationIdentifier l5 = LocationIdentifier.newBuilder()
                .setAlias(OR)
                .build();

        assertNotEquals("Equals should be false", l4, l5);

        LocationAlias OR1 = LocationAlias.newBuilder()
                .setType("us_state")
                .setValue("OR")
                .build();

        LocationIdentifier l6 = LocationIdentifier.newBuilder()
                .setAlias(OR1)
                .build();

        assertEquals("Equals should be true", l5, l6);
    }
}
