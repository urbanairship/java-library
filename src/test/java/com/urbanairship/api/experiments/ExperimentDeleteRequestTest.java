package com.urbanairship.api.experiments;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;

import org.apache.http.HttpHeaders;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExperimentDeleteRequestTest {

    ExperimentDeleteRequest experimentDeleteRequest = ExperimentDeleteRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertNull(experimentDeleteRequest.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(experimentDeleteRequest.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(experimentDeleteRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        assertEquals(experimentDeleteRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/experiments/scheduled/id");
        assertEquals(experimentDeleteRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testExperimentParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(experimentDeleteRequest.getResponseParser().parse(responseJson), genericResponse);
    }
}
