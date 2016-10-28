package com.urbanairship.api.location.model;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationViewTest {

    ObjectNode node = JsonNodeFactory.instance.objectNode();

    @Test
    public void testLocationViewBuilder() {

        List<Double> bounds = new ArrayList<Double>();
        bounds.add(1.0D);
        bounds.add(2.0D);
        bounds.add(3.0D);
        bounds.add(4.0D);

        assertEquals(4, bounds.size());

        BoundedBox testBounds = BoundedBox.newBuilder()
                .setCornerOne(Point.newBuilder()
                        .setLatitude(1.0D)
                        .setLongitude(2.0D)
                        .build())
                .setCornerTwo(Point.newBuilder()
                        .setLatitude(3.0D)
                        .setLongitude(4.0D)
                        .build())
                .build();

        List<Double> center = new ArrayList<Double>();
        center.add(5.0D);
        center.add(6.0D);

        assertEquals(2, center.size());

        Point testPoint = Point.newBuilder()
                .setLatitude(5.0D)
                .setLongitude(6.0D)
                .build();

        node.put("hello", "kitty");

        LocationView target = LocationView.newBuilder()
                .setBounds(bounds)
                .setCentroid(center)
                .setLocationId("ID")
                .setLocationType("Type")
                .setPropertiesNode(node)
                .build();

        assertNotNull(target);
        assertEquals(testBounds, target.getBounds().get());
        assertEquals(testPoint, target.getCentroid().get());
        assertEquals("ID", target.getLocationId());
        assertEquals("Type", target.getLocationType());
        assertEquals("{\"hello\":\"kitty\"}", target.getPropertiesJsonString());
        assertEquals(node, target.getPropertiesJsonNode());
        assertEquals(target.getPropertiesJsonString(), target.getPropertiesJsonNode().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNumberofPointsInBounds() {

        List<Double> bounds = new ArrayList<Double>();
        bounds.add(1.0D);
        bounds.add(2.0D);
        bounds.add(3.0D);

        assertEquals(3, bounds.size());

        List<Double> center = new ArrayList<Double>();
        center.add(5.0D);
        center.add(6.0D);

        assertEquals(2, center.size());

        Point testPoint = Point.newBuilder()
                .setLatitude(5.0D)
                .setLongitude(6.0D)
                .build();

        node.put("hello", "kitty");

        LocationView.newBuilder()
                .setBounds(bounds)
                .setCentroid(center)
                .setLocationId("ID")
                .setLocationType("Type")
                .setPropertiesNode(node)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNumberofPointsInCentoid() {

        List<Double> bounds = new ArrayList<Double>();
        bounds.add(1.0D);
        bounds.add(2.0D);
        bounds.add(3.0D);
        bounds.add(4.0D);

        assertEquals(4, bounds.size());

        List<Double> center = new ArrayList<Double>();
        center.add(5.0D);

        assertEquals(1, center.size());

        Point testPoint = Point.newBuilder()
                .setLatitude(5.0D)
                .setLongitude(6.0D)
                .build();

        node.put("hello", "kitty");

        LocationView.newBuilder()
                .setBounds(bounds)
                .setCentroid(center)
                .setLocationId("ID")
                .setLocationType("Type")
                .setPropertiesNode(node)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPoint() {
        Point.newBuilder()
            .setLatitude(95.0D)
            .setLongitude(6.0D)
            .build();
    }


}
