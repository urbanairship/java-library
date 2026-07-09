package com.urbanairship.api.journeys;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.journeys.model.JourneyTriggerPayload;
import com.urbanairship.api.journeys.parse.JourneysObjectMapper;
import com.urbanairship.api.push.model.audience.Selectors;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JourneyTriggerRequestTest {

    private static final String TRIGGERING_ID = "1fd202ca-9deb-4372-b052-dff0516f9518";
    private static final String SEGMENT_ID = "abc123";
    private static final String ENTRANCE_ID = "product_123";

    private JourneyTriggerRequest minimalRequest;
    private JourneyTriggerRequest fullRequest;

    @Before
    public void setUp() {
        JourneyTriggerPayload minimalPayload = JourneyTriggerPayload.newBuilder()
                .setAudience(Selectors.segment(SEGMENT_ID))
                .setTriggeringId(TRIGGERING_ID)
                .build();
        minimalRequest = JourneyTriggerRequest.newRequest(minimalPayload);

        Map<String, Object> attrs = new HashMap<>();
        attrs.put("product_name", "widget");
        JourneyTriggerPayload fullPayload = JourneyTriggerPayload.newBuilder()
                .setAudience(Selectors.segment(SEGMENT_ID))
                .setTriggeringId(TRIGGERING_ID)
                .setEntranceId(ENTRANCE_ID)
                .setGlobalAttributes(attrs)
                .build();
        fullRequest = JourneyTriggerRequest.newRequest(fullPayload);
    }

    @Test
    public void testHttpMethod() {
        assertEquals(Request.HttpMethod.POST, minimalRequest.getHttpMethod());
        assertEquals(Request.HttpMethod.POST, fullRequest.getHttpMethod());
    }

    @Test
    public void testContentType() {
        assertEquals(ContentType.APPLICATION_JSON, minimalRequest.getContentType());
        assertEquals(ContentType.APPLICATION_JSON, fullRequest.getContentType());
    }

    @Test
    public void testHeaders() {
        Map<String, String> expected = new HashMap<>();
        expected.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        expected.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(expected, minimalRequest.getRequestHeaders());
        assertEquals(expected, fullRequest.getRequestHeaders());
    }

    @Test
    public void testUri() throws Exception {
        URI baseUri = URI.create("https://go.urbanairship.com");
        URI expectedUri = URI.create("https://go.urbanairship.com/api/journeys/trigger");
        assertEquals(expectedUri, minimalRequest.getUri(baseUri));
        assertEquals(expectedUri, fullRequest.getUri(baseUri));
    }

    @Test
    public void testBearerTokenAuthRequired() {
        assertFalse(minimalRequest.bearerTokenAuthRequired());
    }

    @Test
    public void testCanUseBearerTokenAuth() {
        assertFalse(minimalRequest.canUseBearerTokenAuth());
    }

    @Test
    public void testMinimalRequestBody() throws Exception {
        String body = minimalRequest.getRequestBody();
        String expected = JourneysObjectMapper.getInstance().writeValueAsString(
                JourneyTriggerPayload.newBuilder()
                        .setAudience(Selectors.segment(SEGMENT_ID))
                        .setTriggeringId(TRIGGERING_ID)
                        .build()
        );
        assertEquals(expected, body);
    }

    @Test
    public void testFullRequestBody() throws Exception {
        String body = fullRequest.getRequestBody();
        // Verify required fields are present
        assert body.contains("\"triggering_id\"");
        assert body.contains(TRIGGERING_ID);
        assert body.contains("\"entrance_id\"");
        assert body.contains(ENTRANCE_ID);
        assert body.contains("\"global_attributes\"");
        assert body.contains("\"product_name\"");
        assert body.contains("\"widget\"");
    }

    @Test
    public void testMultipleTriggeringIds() throws Exception {
        JourneyTriggerPayload payload = JourneyTriggerPayload.newBuilder()
                .setAudience(Selectors.segment(SEGMENT_ID))
                .addTriggeringId("id-one")
                .addTriggeringId("id-two")
                .build();
        String body = JourneyTriggerRequest.newRequest(payload).getRequestBody();
        assert body.contains("[\"id-one\",\"id-two\"]") || body.contains("[ \"id-one\", \"id-two\" ]");
    }

    @Test
    public void testResponseParser() throws Exception {
        String jsonResponse = "{\"ok\":true}";
        GenericResponse response = minimalRequest.getResponseParser().parse(jsonResponse);
        assert response.getOk().orElse(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGlobalAttributeReservedPrefix() {
        JourneyTriggerPayload.newBuilder()
                .setAudience(Selectors.segment(SEGMENT_ID))
                .setTriggeringId(TRIGGERING_ID)
                .addGlobalAttribute("ua_reserved", "value")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooManyTriggeringIds() {
        JourneyTriggerPayload.Builder builder = JourneyTriggerPayload.newBuilder()
                .setAudience(Selectors.segment(SEGMENT_ID));
        for (int i = 0; i <= 10; i++) {
            builder.addTriggeringId("id-" + i);
        }
        builder.build();
    }
}
