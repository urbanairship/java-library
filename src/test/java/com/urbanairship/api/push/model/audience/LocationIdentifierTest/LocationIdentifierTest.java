package com.urbanairship.api.push.model.audience.LocationIdentifierTest;


import com.urbanairship.api.push.model.audience.location.LocationAlias;
import com.urbanairship.api.push.model.audience.location.LocationIdentifier;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
public class LocationIdentifierTest {

    @Test
    public void testEquals(){
        LocationIdentifier l1= LocationIdentifier.newBuilder()
                                                  .setId("ID")
                                                  .build();

        LocationIdentifier l2 = LocationIdentifier.newBuilder()
                                                  .setId("ID")
                                                  .build();
        assertTrue("Equals should return true",l1.equals(l2));
        LocationIdentifier l3 = LocationIdentifier.newBuilder()
                                                  .setId("foo")
                                                  .build();

        assertFalse("Equals should return false", l1.equals(l3));

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

        assertFalse("Equals should be false", l4.equals(l5));

        LocationAlias OR1 = LocationAlias.newBuilder()
                .setType("us_state")
                .setValue("OR")
                .build();

        LocationIdentifier l6 = LocationIdentifier.newBuilder()
                .setAlias(OR1)
                .build();

        assertTrue("Equals should be true", l5.equals(l6));
    }
}
