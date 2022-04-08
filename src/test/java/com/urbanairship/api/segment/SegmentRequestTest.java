package com.urbanairship.api.segment;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.segments.SegmentRequest;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SegmentRequestTest {
    private static final String TEST_QUERY_PATH = "/api/segments/";
    private static final String TEST_SEGMENT_ID = "abcdefg";

    String displayName = "JavaSoFunWoooooo";
    Selector andSelector = Selectors.tags("java", "lib");
    Selector criteria = Selectors.or(andSelector, Selectors.tag("mfd"));

    DateRange dateRange = Selectors.weeks(3);
    Selector location = Selectors.location("blah", dateRange);
    Selector locationCriteria = Selectors.or(criteria, location);

    SegmentRequest createRequest;
    SegmentRequest updateRequest;
    SegmentRequest createLocationRequest;
    SegmentRequest updateLocationRequest;

    @Before
    public void setup() {
        createRequest = SegmentRequest.newRequest()
                .setCriteria(criteria)
                .setDisplayName(displayName);
        createLocationRequest = SegmentRequest.newRequest()
                .setCriteria(locationCriteria)
                .setDisplayName(displayName);
        updateRequest = SegmentRequest.newUpdateRequest(TEST_SEGMENT_ID)
                .setCriteria(criteria)
                .setDisplayName(displayName);
        updateLocationRequest = SegmentRequest.newUpdateRequest(TEST_SEGMENT_ID)
                .setCriteria(locationCriteria)
                .setDisplayName(displayName);
    }
    
    @Test
    public void testContentType() throws Exception {
        assertEquals(createRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(updateRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(createLocationRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(updateLocationRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(createRequest.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(updateRequest.getHttpMethod(), Request.HttpMethod.PUT);
        assertEquals(createLocationRequest.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(updateLocationRequest.getHttpMethod(), Request.HttpMethod.PUT);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(createRequest.getRequestHeaders(), headers);
        assertEquals(updateRequest.getRequestHeaders(), headers);
        assertEquals(createLocationRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(updateLocationRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedCreateURI = URI.create("https://go.urbanairship.com" + TEST_QUERY_PATH);
        URI expectedUpdateURI = URI.create("https://go.urbanairship.com" + TEST_QUERY_PATH + TEST_SEGMENT_ID);

        assertEquals(createRequest.getUri(baseURI), expectedCreateURI);
        assertEquals(updateRequest.getUri(baseURI), expectedUpdateURI);
        assertEquals(createLocationRequest.getUri(baseURI), expectedCreateURI);
        assertEquals(updateLocationRequest.getUri(baseURI), expectedUpdateURI);
    }

    @Test
    public void testParser() throws Exception {

        ErrorDetails errorDetails = new ErrorDetails("The key chanel is not allowed in this context", null);

        GenericResponse genericResponse = new GenericResponse(true, "1769297b-1640-43a4-af84-3e0ece89efe", "error", errorDetails);

        String responseJson = "{" +
                "\"ok\": true," +
                "\"operation_id\": \"1769297b-1640-43a4-af84-3e0ece89efe\"," +
                "\"error\": \"error\"," +
                "\"details\": {\"error\": \"The key chanel is not allowed in this context\"" +
                "}" +
                "}";

        assertEquals(createRequest.getResponseParser().parse(responseJson), genericResponse);
        assertEquals(updateRequest.getResponseParser().parse(responseJson), genericResponse);

    }
}