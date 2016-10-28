package com.urbanairship.api.location;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.location.model.LocationResponse;
import com.urbanairship.api.location.parse.LocationObjectMapper;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LocationRequestTest {

    private final ObjectMapper mapper = LocationObjectMapper.getInstance();

    private final LocationRequest locationRequest = LocationRequest.newRequest("chicago").setType("city");

    @Test
    public void testContentType() throws Exception {
        assertEquals(locationRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(locationRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(locationRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(locationRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/location/?q=chicago&type=city");
        assertEquals(locationRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testListingParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<LocationResponse>() {
            @Override
            public LocationResponse parse(String response) throws IOException {
                return mapper.readValue(response, LocationResponse.class);
            }
        };

        String response = "{\n" +
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

        assertEquals(locationRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

}