package com.urbanairship.api.channel.email.model;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannelRequest;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
public class RegisterEmailChannelRequestTest {

    RegisterEmailChannel emailChannel = RegisterEmailChannel.newBuilder()
            .setAddress("example@urbanairship.com")
            .setEmailOptInLevel(OptInLevel.EMAIL_COMMERCIAL_OPTED_IN, "2018-11-11T12:00:00")
            .build();

    RegisterEmailChannelRequest request = RegisterEmailChannelRequest.newRequest(emailChannel);

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(request.getRequestBody(), "{\"channel\":{\"type\":\"email\",\"address\":\"example@urbanairship.com\",\"commercial_opted_in\":\"2018-11-11T12:00:00\"}}");
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        Assert.assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/email/");
        Assert.assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testRegisterEmailChannelParser() throws Exception {
        ResponseParser<EmailChannelResponse> responseParser = response -> PushObjectMapper.getInstance().readValue(response, EmailChannelResponse.class);

        String response = "{\"ok\" : true,\"channel_id\" : \"df6a6b50\"}";
        Assert.assertEquals(request.getResponseParser().parse(response), responseParser.parse(response));
    }
}