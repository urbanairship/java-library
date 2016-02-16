package com.urbanairship.api.location.parse;

import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.LocationResponse;
import com.urbanairship.api.location.model.LocationView;
import com.urbanairship.api.location.model.Point;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class LocationResponseViewDeserializerTest {

    private static final ObjectMapper MAPPER = LocationObjectMapper.getInstance();

    @Test
    public void testLocationResponse() {
        String jsonResponse = "{\n" +
            "  \"features\":[\n" +
            "    {\n" +
            "      \"type\":\"Feature\",\n" +
            "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
            "      \"properties\":{\n" +
            "        \"source\":\"tiger.census.gov\",\n" +
            "        \"boundary_type_string\":\"City/Place\",\n" +
            "        \"name\":\"San Francisco\",\n" +
            "        \"context\":{\n" +
            "          \"us_state_name\":\"California\",\n" +
            "          \"us_state\":\"CA\"\n" +
            "        },\n" +
            "        \"boundary_type\":\"city\"\n" +
            "      },\n" +
            "      \"bounds\":[\n" +
            "        37.63983,\n" +
            "        -123.173825,\n" +
            "        37.929824,\n" +
            "        -122.28178\n" +
            "      ],\n" +
            "      \"centroid\":[\n" +
            "        37.759715,\n" +
            "        -122.693976\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\":\"Feature\",\n" +
            "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
            "      \"properties\":{\n" +
            "        \"source\":\"tiger.census.gov\",\n" +
            "        \"boundary_type_string\":\"City/Place\",\n" +
            "        \"name\":\"South San Francisco\",\n" +
            "        \"context\":{\n" +
            "          \"us_state_name\":\"California\",\n" +
            "          \"us_state\":\"CA\"\n" +
            "        },\n" +
            "        \"boundary_type\":\"city\"\n" +
            "      },\n" +
            "      \"bounds\":[\n" +
            "        37.633916,\n" +
            "        -122.471883,\n" +
            "        37.673132,\n" +
            "        -122.220531\n" +
            "      ],\n" +
            "      \"centroid\":[\n" +
            "        37.652731,\n" +
            "        -122.343222\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

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

        try {
            LocationResponse response = MAPPER.readValue(jsonResponse, LocationResponse.class);

            assertNotNull(response);
            assertEquals(2, response.getFeatures().get().size());

            assertEquals(point, response.getFeatures().get().get(0).getCentroid().get());
            assertEquals(boundedBox, response.getFeatures().get().get(0).getBounds().get());
            assertEquals("Feature", response.getFeatures().get().get(0).getLocationType());
            assertEquals("4oFkxX7RcUdirjtaenEQIV", response.getFeatures().get().get(0).getLocationId());

            assertEquals(southPoint, response.getFeatures().get().get(1).getCentroid().get());
            assertEquals(southBoundedBox, response.getFeatures().get().get(1).getBounds().get());
            assertEquals("Feature", response.getFeatures().get().get(1).getLocationType());
            assertEquals("44jJFKMJg1oeYvv9SImLEx", response.getFeatures().get().get(1).getLocationId());

            LocationView sanFrancisco = response.getFeatures().get().get(0);
            assertEquals("\"San Francisco\"", sanFrancisco.getPropertiesJsonNode().get("name").toString());

            LocationView southSanFrancisco = response.getFeatures().get().get(1);
            assertEquals("\"South San Francisco\"", southSanFrancisco.getPropertiesJsonNode().get("name").toString());

        } catch (Exception e) {
            fail("Exception " + e.getMessage());
        }
    }
}
