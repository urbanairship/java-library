package com.urbanairship.api.experiments;

import com.urbanairship.api.client.ResponseParser;
import org.apache.http.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExperimentDeleteRequestTest {

    ExperimentDeleteRequest experimentDeleteRequest = ExperimentDeleteRequest.newRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertEquals(experimentDeleteRequest.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(experimentDeleteRequest.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(experimentDeleteRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
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
        ResponseParser<String> responseParser = new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };

        assertEquals(experimentDeleteRequest.getResponseParser().parse(null), responseParser.parse(null));
    }
}
