package com.urbanairship.api.tags;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TagListRequestTest {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static String TAG_LIST_CREATE_PATH = "/api/tag-lists";

     TagListRequest createRequest;

    @Before
    public void setupCreate() {
        createRequest =  TagListRequest.newRequest()
                .setName("abc123")
                .setDescription("create description")
                .addExtra("key1", "val1")
                .addExtra("key2", "val2")
                .addTags("test", ImmutableSet.of("tptp","totot"))
                .removeTags("test", ImmutableSet.of("tptp","totot"))
                .setTags("test", ImmutableSet.of("tptp","totot"));
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

        String createString = "{" +
                "\"extra\":{" +
                "\"key2\":\"val2\"," +
                "\"key1\":\"val1\"" +
                "}," +
                "\"description\":\"create description\"," +
                "\"name\":\"abc123\"," +
                "\"add\":{" +
                "\"test\":[\"tptp\",\"totot\"]" +
                "}," +
                "\"remove\":{" +
                "\"test\":[\"tptp\",\"totot\"]" +
                "}," +
                "\"set\":{" +
                "\"test\":[\"tptp\",\"totot\"]" +
                "}" +
                "}";

        assertEquals(MAPPER.readTree(createRequest.getRequestBody()), MAPPER.readTree(createString));
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(createRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedCreateUri = URI.create("https://go.urbanairship.com" + TAG_LIST_CREATE_PATH);
        assertEquals(createRequest.getUri(baseURI), expectedCreateUri);
    }

    @Test
    public void testParser() throws Exception {
        String response = "{\"ok\": true}";
        assertEquals(response, createRequest.getResponseParser().parse(response));
    }
}
