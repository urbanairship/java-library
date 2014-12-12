package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.location.model.Location;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class APILocationResponseTest {

    ObjectMapper mapper = APIResponseObjectMapper.getInstance();

    @Test
    public void testAPILocationResponse() {
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

        // Just testing whether "features" gets deserialized properly in APILocationResponse.  Deserialization of
        // Location tested in LocationTest and LocationDeserializerTest

        try {

            APILocationResponse response = mapper.readValue(jsonResponse, APILocationResponse.class);
            assertNotNull(response);
            assertEquals(2, response.getFeatures().get().size());

            Location sanFrancisco = response.getFeatures().get().get(0);
            assertEquals("\"San Francisco\"", sanFrancisco.getPropertiesJsonNode().get("name").toString());

            Location southSanFrancisco = response.getFeatures().get().get(1);
            assertEquals("\"South San Francisco\"", southSanFrancisco.getPropertiesJsonNode().get("name").toString());

        } catch (Exception e) {
            fail("Exception " + e.getMessage());
        }
    }

}
