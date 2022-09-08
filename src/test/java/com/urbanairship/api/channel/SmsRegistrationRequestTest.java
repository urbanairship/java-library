package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.SmsRegistrationResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SmsRegistrationRequestTest {
    DateTime dateTime = DateTime.now();
    ObjectMapper mapper = ChannelObjectMapper.getInstance();

    SmsRegistrationRequest registrationRequestWithDate = SmsRegistrationRequest.newRegistrationRequest("sender", "msisdn", dateTime);
    SmsRegistrationRequest registrationRequest = SmsRegistrationRequest.newRegistrationRequest("sender", "msisdn");
    SmsRegistrationRequest uninstallRequest = SmsRegistrationRequest.newUninstallRequest("sender", "msisdn");
    SmsRegistrationRequest optOutRequest = SmsRegistrationRequest.newOptOutRequest("sender", "msisdn");

    URI baseURI = URI.create("https://go.urbanairship.com");

    @Test
    public void testHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(headers, registrationRequest.getRequestHeaders());
        assertEquals(headers, uninstallRequest.getRequestHeaders());
        assertEquals(headers, optOutRequest.getRequestHeaders());
        assertEquals(headers, registrationRequestWithDate.getRequestHeaders());
    }

    @Test
    public void testMethod() {
        assertEquals(Request.HttpMethod.POST, registrationRequest.getHttpMethod());
        assertEquals(Request.HttpMethod.POST, uninstallRequest.getHttpMethod());
        assertEquals(Request.HttpMethod.POST, optOutRequest.getHttpMethod());
        assertEquals(Request.HttpMethod.POST, registrationRequestWithDate.getHttpMethod());
    }

    @Test
    public void testContentType() {
        assertEquals(ContentType.APPLICATION_JSON, registrationRequest.getContentType());
        assertEquals(ContentType.APPLICATION_JSON, uninstallRequest.getContentType());
        assertEquals(ContentType.APPLICATION_JSON, optOutRequest.getContentType());
        assertEquals(ContentType.APPLICATION_JSON, registrationRequestWithDate.getContentType());
    }

    @Test
    public void testRegistrationURI() throws URISyntaxException {
        URI expectedUri = URI.create("https://go.urbanairship.com/api/channels/sms");
        assertEquals(expectedUri, registrationRequest.getUri(baseURI));
        assertEquals(expectedUri, registrationRequestWithDate.getUri(baseURI));
    }

    @Test
    public void testUninstallURI() throws URISyntaxException {
        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/sms/uninstall");
        assertEquals(expectedURI, uninstallRequest.getUri(baseURI));
    }

    @Test
    public void testOptOutURI() throws URISyntaxException {
        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/sms/opt-out");
        assertEquals(expectedURI, optOutRequest.getUri(baseURI));
    }

    @Test
    public void testSmsRequestParser() throws IOException {
        ResponseParser<SmsRegistrationResponse> parser = response -> mapper.readValue(response, SmsRegistrationResponse.class);

        String channelResponse = "{\n" +
                "\"ok\": true,\n" +
                "\"channel_id\": \"7c5d7328-9bb4-4ff7-86b0-96a5f1da5868\"\n" +
                "}";

        String registrationResponse = "{\n" +
                "\"ok\": true,\n" +
                "\"status\": \"pending\"\n" +
                "}";

        String badRequestResponse = "{\n" +
                "\"ok\": false,\n" +
                "\"errors\": \"Unable to retrieve details for sender 12345 with app_key <application key>\"\n" +
                "}";

        assertEquals(parser.parse(channelResponse), registrationRequestWithDate.getResponseParser().parse(channelResponse));
        assertEquals(parser.parse(registrationResponse), registrationRequest.getResponseParser().parse(registrationResponse));
        assertEquals(parser.parse(badRequestResponse), registrationRequest.getResponseParser().parse(badRequestResponse));
    }
}
