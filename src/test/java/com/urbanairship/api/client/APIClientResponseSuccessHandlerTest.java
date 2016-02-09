package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class APIClientResponseSuccessHandlerTest {

    @Test
    public void testListTagsHandleSuccess() {

        String listtagresponse = "{\"tags\":[\"Puppies\",\"Kitties\",\"GrumpyCat\"]}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(listtagresponse.getBytes()),
            listtagresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIListTagsResponse> handler =
            new APIClientResponseHandler<APIListTagsResponse>(APIListTagsResponse.class);

        try {
            APIClientResponse<APIListTagsResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("Tags incorrect",
                response.getApiResponse().getTags().contains("Puppies") &&
                    response.getApiResponse().getTags().contains("Kitties") &&
                    response.getApiResponse().getTags().contains("GrumpyCat"));
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testLocationHandleSuccess() {

        String locationResponse = "{\n" +
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
            "    }\n" +
            "  ]\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(locationResponse.getBytes()),
            locationResponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APILocationResponse> handler =
            new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class);

        try {
            APIClientResponse<APILocationResponse> response =
                handler.handleResponse(httpResponse);
            assertNotNull(response);
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

}
