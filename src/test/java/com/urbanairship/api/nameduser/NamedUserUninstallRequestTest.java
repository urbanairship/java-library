package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.common.parse.CommonObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class NamedUserUninstallRequestTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testUninstallNamedUser() throws Exception {

        NamedUserUninstallRequest request = NamedUserUninstallRequest
            .newUninstallRequest(ImmutableList.of("user-id-1234","user-id-5678"));

        String expected = "{\"named_user_id\":[\"user-id-1234\",\"user-id-5678\"]}";

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }


    NamedUserUninstallRequest request = NamedUserUninstallRequest
    .newUninstallRequest(ImmutableList.of("user-id-1234","user-id-5678"));

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/named_users/uninstall/");
        assertEquals(request.getUri(baseURI), expextedURI);
    }

    @Test
    public void testResponseParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(request.getResponseParser().parse(responseJson), genericResponse);
    }
}