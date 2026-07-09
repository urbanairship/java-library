package com.urbanairship.api.journeys;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.journeys.model.JourneyExitPayload;
import com.urbanairship.api.journeys.parse.JourneysObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JourneyExitRequestTest {

    private static final String TRIGGERING_ID = "1fd202ca-9deb-4372-b052-dff0516f9518";
    private static final String ENTRANCE_ID = "product_123";

    private JourneyExitRequest minimalRequest;
    private JourneyExitRequest withEntranceIdRequest;

    @Before
    public void setUp() {
        JourneyExitPayload minimalPayload = JourneyExitPayload.newBuilder()
                .setTriggeringId(TRIGGERING_ID)
                .build();
        minimalRequest = JourneyExitRequest.newRequest(minimalPayload);

        JourneyExitPayload withEntrancePayload = JourneyExitPayload.newBuilder()
                .setTriggeringId(TRIGGERING_ID)
                .setEntranceId(ENTRANCE_ID)
                .build();
        withEntranceIdRequest = JourneyExitRequest.newRequest(withEntrancePayload);
    }

    @Test
    public void testHttpMethod() {
        assertEquals(Request.HttpMethod.POST, minimalRequest.getHttpMethod());
        assertEquals(Request.HttpMethod.POST, withEntranceIdRequest.getHttpMethod());
    }

    @Test
    public void testContentType() {
        assertEquals(ContentType.APPLICATION_JSON, minimalRequest.getContentType());
        assertEquals(ContentType.APPLICATION_JSON, withEntranceIdRequest.getContentType());
    }

    @Test
    public void testHeaders() {
        Map<String, String> expected = new HashMap<>();
        expected.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        expected.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(expected, minimalRequest.getRequestHeaders());
        assertEquals(expected, withEntranceIdRequest.getRequestHeaders());
    }

    @Test
    public void testUri() throws Exception {
        URI baseUri = URI.create("https://go.urbanairship.com");
        URI expectedUri = URI.create("https://go.urbanairship.com/api/journeys/exit");
        assertEquals(expectedUri, minimalRequest.getUri(baseUri));
        assertEquals(expectedUri, withEntranceIdRequest.getUri(baseUri));
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
                JourneyExitPayload.newBuilder()
                        .setTriggeringId(TRIGGERING_ID)
                        .build()
        );
        assertEquals(expected, body);
    }

    @Test
    public void testRequestBodyWithEntranceId() throws Exception {
        String body = withEntranceIdRequest.getRequestBody();
        assert body.contains("\"triggering_id\"");
        assert body.contains(TRIGGERING_ID);
        assert body.contains("\"entrance_id\"");
        assert body.contains(ENTRANCE_ID);
    }

    @Test
    public void testMinimalRequestBodyDoesNotContainEntranceId() throws Exception {
        String body = minimalRequest.getRequestBody();
        assertFalse(body.contains("entrance_id"));
    }

    @Test
    public void testMultipleTriggeringIds() throws Exception {
        JourneyExitPayload payload = JourneyExitPayload.newBuilder()
                .addTriggeringId("id-one")
                .addTriggeringId("id-two")
                .build();
        String body = JourneyExitRequest.newRequest(payload).getRequestBody();
        assert body.contains("id-one");
        assert body.contains("id-two");
    }

    @Test
    public void testResponseParser() throws Exception {
        String jsonResponse = "{\"ok\":true}";
        GenericResponse response = minimalRequest.getResponseParser().parse(jsonResponse);
        assert response.getOk().orElse(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooManyTriggeringIds() {
        JourneyExitPayload.Builder builder = JourneyExitPayload.newBuilder();
        for (int i = 0; i <= 10; i++) {
            builder.addTriggeringId("id-" + i);
        }
        builder.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyEntranceId() {
        JourneyExitPayload.newBuilder()
                .setTriggeringId(TRIGGERING_ID)
                .setEntranceId("")
                .build();
    }
}
