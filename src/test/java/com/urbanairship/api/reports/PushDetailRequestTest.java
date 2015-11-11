package com.urbanairship.api.reports;

import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PushDetailRequestTest {
    String queryPathString = "/api/reports/perpush/detail/";

    @Test
    public void testBody() throws Exception {
        String pushId1 = UUID.randomUUID().toString();
        String pushId2 = UUID.randomUUID().toString();

        PushDetailRequest request = PushDetailRequest.newRequest()
                .addPushIds(pushId1, pushId2);

        String expected = "{" + "\"push_ids\":" +
                "[\"" + pushId1 + "\",\"" + pushId2 + "\"]" +
                "}";

        assertEquals(expected, request.getRequestBody());
    }


    private PushDetailRequest setup() {
        String pushId1 = UUID.randomUUID().toString();
        String pushId2 = UUID.randomUUID().toString();
        String pushId3 = UUID.randomUUID().toString();

        return PushDetailRequest.newRequest()
                .addPushIds(pushId1,pushId2,pushId3);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(setup().getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(setup().getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(setup().getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com" + queryPathString);
        assertEquals(setup().getUri(baseURI), expectedURI);
    }

}
