package com.urbanairship.api.location.model;


import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationResponseTest {

    @Test
    public void testLocationResponse() {

        Point cornerOne = Point.newBuilder()
            .setLatitude(37.63983)
            .setLongitude(-123.173825)
            .build();

        Point cornerTwo = Point.newBuilder()
            .setLatitude(37.929824)
            .setLongitude(-122.28178)
            .build();

        BoundedBox boundedBox = BoundedBox.newBuilder()
            .setCornerOne(cornerOne)
            .setCornerTwo(cornerTwo)
            .build();

        Point point = Point.newBuilder()
            .setLatitude(37.759715)
            .setLongitude(-122.693976)
            .build();

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("field", "value");

        LocationView sanFrancisco = LocationView.newBuilder()
            .setBounds(boundedBox)
            .setCentroid(point)
            .setLocationId("4oFkxX7RcUdirjtaenEQIV")
            .setLocationType("Feature")
            .setPropertiesNode(node)
            .build();

        Point southCornerOne = Point.newBuilder()
            .setLatitude(37.633916)
            .setLongitude(-122.471883)
            .build();

        Point southCornerTwo = Point.newBuilder()
            .setLatitude(37.673132)
            .setLongitude(-122.220531)
            .build();

        BoundedBox southBoundedBox = BoundedBox.newBuilder()
            .setCornerOne(southCornerOne)
            .setCornerTwo(southCornerTwo)
            .build();

        Point southPoint = Point.newBuilder()
            .setLatitude(37.652731)
            .setLongitude(-122.343222)
            .build();

        ObjectNode southNode = JsonNodeFactory.instance.objectNode();
        node.put("field", "value");

        LocationView southSanFrancisco = LocationView.newBuilder()
            .setBounds(southBoundedBox)
            .setCentroid(southPoint)
            .setLocationId("44jJFKMJg1oeYvv9SImLEx")
            .setLocationType("Feature")
            .setPropertiesNode(southNode)
            .build();

        LocationResponse response = LocationResponse.newBuilder()
            .addAllFeatures(Arrays.asList(sanFrancisco, southSanFrancisco))
            .build();

        assertNotNull(response);
        assertEquals(2, response.getFeatures().get().size());

        assertEquals(point, response.getFeatures().get().get(0).getCentroid().get());
        assertEquals(boundedBox, response.getFeatures().get().get(0).getBounds().get());
        assertEquals("Feature", response.getFeatures().get().get(0).getLocationType());
        assertEquals("4oFkxX7RcUdirjtaenEQIV", response.getFeatures().get().get(0).getLocationId());
        assertEquals(node.toString(), response.getFeatures().get().get(0).getPropertiesJsonString());

        assertEquals(southPoint, response.getFeatures().get().get(1).getCentroid().get());
        assertEquals(southBoundedBox, response.getFeatures().get().get(1).getBounds().get());
        assertEquals("Feature", response.getFeatures().get().get(1).getLocationType());
        assertEquals("44jJFKMJg1oeYvv9SImLEx", response.getFeatures().get().get(1).getLocationId());
        assertEquals(southNode.toString(), response.getFeatures().get().get(1).getPropertiesJsonString());
    }

}
