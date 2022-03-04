package com.urbanairship.api.attributelists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AttributeListsCreateRequestTest {
    private final static ObjectMapper MAPPER = StaticListsObjectMapper.getInstance();
    private final static String ATTRIBUTE_LISTS_CREATE_PATH = "/api/attribute-lists/";

    AttributeListsCreateRequest createRequest;

    @Before
    public void setupCreate() {
        createRequest = AttributeListsCreateRequest.newRequest("ua_attributes_abc123")
                .setDescription("create description")
                .addExtra("key1", "val1")
                .addExtra("key2", "val2");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(createRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(createRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        String NAME_KEY = "name";
        String DESCRIPTION_KEY = "description";
        String EXTRAS_KEY = "extra";


        String createString = "{" +
                "\"extra\":{" +
                "\"key2\":\"val2\"," +
                "\"key1\":\"val1\"" +
                "}," +
                "\"description\":\"create description\"," +
                "\"name\":\"ua_attributes_abc123\"" +
                "}";

        Map<String, Object> createPayload = new HashMap<String, Object>();
        Map<String, String> createExtras = new HashMap<String, String>();
        createPayload.put(NAME_KEY, "ua_attributes_abc123");
        createPayload.put(DESCRIPTION_KEY, "create description");
        createExtras.put("key1", "val1");
        createExtras.put("key2", "val2");
        createPayload.put(EXTRAS_KEY, createExtras);

        String createPayloadString = MAPPER.writeValueAsString(createPayload);

        assertEquals(MAPPER.readTree(createPayloadString), MAPPER.readTree(createString));
        assertEquals(MAPPER.readTree(createRequest.getRequestBody()), MAPPER.readTree(createPayloadString));
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(createRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedCreateUri = URI.create("https://go.urbanairship.com" + ATTRIBUTE_LISTS_CREATE_PATH);
        assertEquals(createRequest.getUri(baseURI), expectedCreateUri);
    }

    public void testParser() throws Exception {
        String response = "{\"ok\": true}";
        assertEquals(response, createRequest.getResponseParser().parse(response));
    }
}
