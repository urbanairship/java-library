package com.urbanairship.api.location.parse;


import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.location.model.Location;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationDeserializerTest {

    private static final ObjectMapper MAPPER = APIResponseObjectMapper.getInstance();

    @Test
    public void testDeserialization() throws Exception {

        String json = "{\n" +
                "   \"type\":\"Feature\",\n" +
                "   \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "   \"properties\":{\n" +
                "      \"source\":\"tiger.census.gov\",\n" +
                "      \"boundary_type_string\":\"City/Place\",\n" +
                "      \"name\":\"San Francisco\",\n" +
                "      \"context\":{\n" +
                "         \"us_state_name\":\"California\",\n" +
                "         \"us_state\":\"CA\"\n" +
                "      },\n" +
                "      \"boundary_type\":\"city\"\n" +
                "   },\n" +
                "   \"bounds\":[\n" +
                "      37.63983,\n" +
                "      -123.173825,\n" +
                "      37.929824,\n" +
                "      -122.28178\n" +
                "   ],\n" +
                "   \"centroid\":[\n" +
                "      37.759715,\n" +
                "      -122.693976\n" +
                "   ]\n" +
                "}";

        Location target = MAPPER.readValue(json, Location.class);

        assertNotNull(target);
        assertEquals(
                "BoundedBox{cornerOne=Point{latitude=37.63983, longitude=-123.173825}, cornerTwo=Point{latitude=37.929824, longitude=-122.28178}}",
                target.getBounds().get().toString());
        assertEquals("Point{latitude=37.759715, longitude=-122.693976}", target.getCentroid().get().toString());
        assertEquals("4oFkxX7RcUdirjtaenEQIV", target.getLocationId());
        assertEquals("Feature", target.getLocationType());
        assertEquals(
                "{\"source\":\"tiger.census.gov\",\"boundary_type_string\":\"City/Place\",\"name\":\"San Francisco\",\"context\":{\"us_state_name\":\"California\",\"us_state\":\"CA\"},\"boundary_type\":\"city\"}",
                target.getPropertiesJsonString());

    }

    @Test(expected = APIParsingException.class)
    public void testImproperData() throws Exception {

        String json = "{\n" +
                "   \"type\":\"Feature\",\n" +
                "   \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "   \"properties\":{\n" +
                "      \"source\":\"tiger.census.gov\",\n" +
                "      \"boundary_type_string\":\"City/Place\",\n" +
                "      \"name\":\"San Francisco\",\n" +
                "      \"context\":{\n" +
                "         \"us_state_name\":\"California\",\n" +
                "         \"us_state\":\"CA\"\n" +
                "      },\n" +
                "      \"boundary_type\":\"city\"\n" +
                "   },\n" +
                "   \"bounds\":[\n" +
                "      37.63983,\n" +
                "      -123.173825,\n" +
                "      37.929824\n" +
                "   ],\n" +
                "   \"centroid\":[\n" +
                "      -122.693976\n" +
                "   ]\n" +
                "}";

        Location target = MAPPER.readValue(json, Location.class);
        assertNotNull(target);
    }
}
