package com.urbanairship.api.channel.email.model;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.TrackingOptInLevel;
import com.urbanairship.api.channel.model.email.UpdateEmailChannel;
import com.urbanairship.api.channel.model.email.UpdateEmailChannelRequest;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UpdateEmailChannelRequestTest {

    UpdateEmailChannel emailChannel = UpdateEmailChannel.newBuilder()
            .setAddress("example@urbanairship.com")
            .setEmailOptInLevel(OptInLevel.EMAIL_COMMERCIAL_OPTED_IN, "2018-11-11T12:00:00")
            .setTrackingOptInLevel(TrackingOptInLevel.OPEN_TRACKING_OPTED_IN, "2021-01-01T00:00:00")
            .setTrackingOptInLevel(TrackingOptInLevel.CLICK_TRACKING_OPTED_OUT, "2021-02-02T00:00:00")
            .build();

    UpdateEmailChannelRequest request = UpdateEmailChannelRequest.newRequest("6c8f1d3a-64d8-4ef9-b7a1-9b128013327e",
            emailChannel);

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
        assertEquals(
                request.getRequestBody(),
                "{\"channel\":{\"type\":\"email\",\"address\":\"example@urbanairship.com\",\"commercial_opted_in\":\"2018-11-11T12:00:00\",\"open_tracking_opted_in\":\"2021-01-01T00:00:00\",\"click_tracking_opted_out\":\"2021-02-02T00:00:00\"}}");
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

        URI expectedURI = URI
                .create("https://go.urbanairship.com/api/channels/email/6c8f1d3a-64d8-4ef9-b7a1-9b128013327e");
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testRegisterEmailChannelParser() throws Exception {
        ResponseParser<EmailChannelResponse> responseParser = response -> PushObjectMapper.getInstance()
                .readValue(response, EmailChannelResponse.class);

        String response = "{\"ok\" : true,\"channel_id\" : \"6c8f1d3a-64d8-4ef9-b7a1-9b128013327e\"}";
        assertEquals(request.getResponseParser().parse(response), responseParser.parse(response));
    }
}