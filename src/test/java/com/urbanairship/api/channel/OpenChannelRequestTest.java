package com.urbanairship.api.channel;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.OpenChannelResponse;
import com.urbanairship.api.channel.model.open.Channel;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.channel.model.open.OpenChannelPayload;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OpenChannelRequestTest {
    OpenChannel openChannel = OpenChannel.newBuilder()
            .setOpenPlatformName("email")
            .setOldAddress("old_email@example.come")
            .addIdentifier("com.example.external_id", "df6a6b50-9843-7894-1235-12aed4489489")
            .build();

    Channel channel = Channel.newBuilder()
            .setOpenChannel(openChannel)
            .setChannelType(ChannelType.OPEN)
            .setOptIn(true)
            .setAddress("new_email@example.com")
            .setTags(true)
            .addTag("asdf")
            .setTimeZone("America/Los_Angeles")
            .setLocaleCountry("US")
            .setLocaleLanguage("en")
            .build();

    OpenChannelPayload payload = new OpenChannelPayload(channel);
    OpenChannelRequest openChannelRequest = OpenChannelRequest.newRequest(payload);

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(openChannelRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(openChannelRequest.getRequestBody(), payload.toJSON());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(openChannelRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(openChannelRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/channels/open/");
        assertEquals(openChannelRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testCustomEventParser() throws Exception {
        ResponseParser<OpenChannelResponse> responseParser = new ResponseParser<OpenChannelResponse>() {
            @Override
            public OpenChannelResponse parse(String response) throws IOException {
                return ChannelObjectMapper.getInstance().readValue(response, OpenChannelResponse.class);
            }
        };

        String response = "{\"ok\" : true,\"channel_id\" : \"df6a6b50-9843-0304-d5a5-743f246a8567\"}";
        assertEquals(openChannelRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
