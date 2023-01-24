package com.urbanairship.api.channel.email.model;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.OptInMode;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.ReplaceEmailChannelRequest;
import com.urbanairship.api.channel.model.email.UpdateEmailChannel;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ReplaceEmailChannelRequestTest {

    private final RegisterEmailChannel emailChannel = RegisterEmailChannel.newBuilder()
            .setAddress("example@urbanairship.com")
            .setEmailOptInLevel(OptInLevel.EMAIL_COMMERCIAL_OPTED_IN, "2018-11-11T12:00:00")
            .setEmailOptInMode(OptInMode.CLASSIC)
            .build();

    private final ReplaceEmailChannelRequest request = ReplaceEmailChannelRequest.newRequest("6c8f1d3a-64d8-4ef9-b7a1-9b128013327e", emailChannel);

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), "{\"channel\":{\"type\":\"email\",\"address\":\"example@urbanairship.com\",\"commercial_opted_in\":\"2018-11-11T12:00:00\"},\"opt_in_mode\":\"classic\"}");
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

        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/email/replace/6c8f1d3a-64d8-4ef9-b7a1-9b128013327e");
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testRegisterEmailChannelParser() throws Exception {
        ResponseParser<EmailChannelResponse> responseParser = response -> PushObjectMapper.getInstance().readValue(response, EmailChannelResponse.class);

        String response = "{\"ok\" : true,\"channel_id\" : \"6c8f1d3a-64d8-4ef9-b7a1-9b128013327e\"}";
        assertEquals(request.getResponseParser().parse(response), responseParser.parse(response));
    }
}