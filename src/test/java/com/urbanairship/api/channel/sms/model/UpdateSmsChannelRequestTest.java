package com.urbanairship.api.channel.sms.model;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.sms.UpdateSmsChannel;
import com.urbanairship.api.channel.model.sms.UpdateSmsChannelRequest;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UpdateSmsChannelRequestTest {

    UpdateSmsChannel updateSmsChannel = UpdateSmsChannel.newBuilder()
        .setMsisdn("13609048615")
        .setSender("17372004196")
        .setOptedIn(DateTime.parse("2021-10-11T02:03:03.000Z"))
        .setLocaleCountry("US")
        .setLocaleLanguage("en")
        .setTimeZone("America/Los_Angeles")
        .build();

    UpdateSmsChannelRequest request = UpdateSmsChannelRequest.newRequest("308303cf-9c10-4d71-9bc2-d9f3a671ed0c", updateSmsChannel);

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.PUT);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), "{\"msisdn\":\"13609048615\",\"sender\":\"17372004196\",\"opted_in\":\"2021-10-11T02:03:03\",\"timezone\":\"America/Los_Angeles\",\"locale_country\":\"US\",\"locale_language\":\"en\"}");
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/sms/308303cf-9c10-4d71-9bc2-d9f3a671ed0c");
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testPushParser() throws Exception {
        String response = "{\"ok\" : true}";
        assertEquals(response, request.getResponseParser().parse(response));
    }
}