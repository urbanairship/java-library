package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChannelRequestTest {

    ObjectMapper mapper = ChannelObjectMapper.getInstance();

    ChannelRequest listAllRequest = ChannelRequest.newRequest();
    ChannelRequest listRequest = ChannelRequest.newRequest("channel");
    ChannelRequest listNextPageChannelRequest = ChannelRequest.newRequest(URI.create("https://go.urbanairship.com/api/channels/?limit=1000&start=c238b2d2-996a-4ef3-ab54-1d4dc5bca99a"));


    @Test
    public void testContentType() throws Exception {
        assertEquals(listAllRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(listRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(listNextPageChannelRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(listAllRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listNextPageChannelRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(listAllRequest.getRequestBody(), null);
        assertNull(listRequest.getRequestBody());
        assertNull(listNextPageChannelRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(listAllRequest.getRequestHeaders(), headers);
        assertEquals(listRequest.getRequestHeaders(), headers);
        assertEquals(listNextPageChannelRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/");
        assertEquals(listAllRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/channels/channel");
        assertEquals(listRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/channels/?limit=1000&start=c238b2d2-996a-4ef3-ab54-1d4dc5bca99a");
        assertEquals(listNextPageChannelRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testChannelListingParser() throws Exception {
        ResponseParser<ChannelResponse> responseParser = response -> mapper.readValue(response, ChannelResponse.class);

        String response = "{\n" +
            "  \"ok\": true,\n" +
            "  \"channels\": [\n" +
            "    {\n" +
            "      \"channel_id\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "      \"device_type\": \"android\",\n" +
            "      \"installed\": false,\n" +
            "      \"opt_in\": false,\n" +
            "      \"push_address\": null,\n" +
            "      \"created\": \"2012-06-05T20:37:37\",\n" +
            "      \"last_registration\": null,\n" +
            "      \"alias\": null,\n" +
            "      \"tags\": [\n" +
            "        \"test01\"\n" +
            "      ],\n" +
            "      \"tag_groups\": {\n" +
            "        \"testGroup01\" : [\n" +
            "          \"testGroup01Tag01\"\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"channel_id\": \"00662346-9e39-4f5f-80e7-3f8fae58863c\",\n" +
            "      \"device_type\": \"android\",\n" +
            "      \"installed\": true,\n" +
            "      \"opt_in\": true,\n" +
            "      \"background\": true,\n" +
            "      \"push_address\": \"APA91bFPOUF6KNHXjoG0vaQSP4VLXirGDpy0_CRcb6Jhvnrya2bdRmlUoMiJ12JJevjONZzUwFETYa8uzyiE_9WaL3mzZrdjqOv2YuzYlQ_TrXVgo61JmIyw-M_pshIjVvkvtOuZ4MnRJJ_MiQDYwpB4ZhOTMlyqRw\",\n" +
            "      \"created\": \"2014-03-06T18:52:59\",\n" +
            "      \"last_registration\": \"2014-10-07T21:28:35\",\n" +
            "      \"alias\": \"aaron-device\",\n" +
            "      \"tags\": [\n" +
            "        \"aaron-tag\",\n" +
            "        \"rhtgeg\",\n" +
            "        \"tnrvrg\"\n" +
            "      ],\n" +
            "      \"tag_groups\": {\n" +
            "        \"testGroup02\" : [\n" +
            "          \"testGroup02Tag01\",\n" +
            "          \"testGroup02Tag02\"\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"channel_id\": \"00d174cd-0a31-427e-95c9-52d5785bcd50\",\n" +
            "      \"device_type\": \"ios\",\n" +
            "      \"installed\": true,\n" +
            "      \"opt_in\": true,\n" +
            "      \"background\": true,\n" +
            "      \"push_address\": \"E4EA0D96092A9213BB186BEF66E83EE226401F82B3A77A1AC8217A8FE8ED4614\",\n" +
            "      \"created\": \"2014-07-09T18:08:37\",\n" +
            "      \"last_registration\": \"2014-10-02T01:41:42\",\n" +
            "      \"alias\": null,\n" +
            "      \"tags\": [\n" +
            "        \"version_1.5.0\"\n" +
            "      ],\n" +
            "      \"tag_groups\": {},\n" +
            "      \"ios\": {\n" +
            "        \"badge\": 1,\n" +
            "        \"quiettime\": {\n" +
            "          \"start\": \"17:00\",\n" +
            "          \"end\": \"9:00\"\n" +
            "        },\n" +
            "        \"tz\": \"America\\/Los_Angeles\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"channel_id\": \"00d8cb94-eac9-49fb-bad0-29298a06730e\",\n" +
            "      \"device_type\": \"ios\",\n" +
            "      \"installed\": false,\n" +
            "      \"opt_in\": false,\n" +
            "      \"push_address\": \"21F34C9ED37EAF8D7DC43561C07AA398CA5C6F503196C9E8230C50C0959B8653\",\n" +
            "      \"created\": \"2014-02-22T22:48:37\",\n" +
            "      \"last_registration\": null,\n" +
            "      \"alias\": \"iPhone 7,1\",\n" +
            "      \"tags\": [\n" +
            "        \"kablam\",\n" +
            "        \"version_1.3\"\n" +
            "      ],\n" +
            "      \"tag_groups\": {\n" +
            "        \"testGroup03\": [\n" +
            "          \"testGroup03Tag01\",\n" +
            "          \"testGroup03Tag02\",\n" +
            "          \"testGroup03Tag03\"\n" +
            "        ],\n" +
            "        \"testGroup04\": [\n" +
            "          \"testGroup04Tag01\"\n" +
            "        ]\n" +
            "      },\n" +
            "      \"ios\": {\n" +
            "        \"badge\": 1,\n" +
            "        \"quiettime\": {\n" +
            "          \"start\": null,\n" +
            "          \"end\": null\n" +
            "        },\n" +
            "        \"tz\": null\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"channel_id\": \"01257ecd-8182-41fe-a741-9fed91b993cb\",\n" +
            "      \"device_type\": \"android\",\n" +
            "      \"installed\": false,\n" +
            "      \"opt_in\": false,\n" +
            "      \"push_address\": null,\n" +
            "      \"created\": \"2013-01-25T00:55:05\",\n" +
            "      \"last_registration\": null,\n" +
            "      \"alias\": null,\n" +
            "      \"commercial_opted_in\": \"2013-01-25T00:55:05\",\n" +
            "      \"commercial_opted_out\": \"2013-01-25T00:55:05\",\n" +
            "      \"transactional_opted_in\": \"2013-01-25T00:55:05\",\n" +
            "      \"transactional_opted_out\": \"2013-01-25T00:55:05\",\n" +
            "      \"tags\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"tag_groups\": {}\n" +
            "    }\n" +
            "  ],\n" +
            "  \"next_page\": \"https:\\/\\/go.urbanairship.com\\/api\\/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf\"\n" +
            "}";

        assertEquals(listAllRequest.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(listNextPageChannelRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

    @Test
    public void testChannelLookupParser() throws Exception {
        ResponseParser<ChannelResponse> responseParser = response -> mapper.readValue(response, ChannelResponse.class);


        String response = "{  \n" +
            "  \"ok\": true, \n" +
            "  \"channel\":{  \n" +
            "    \"channel_id\":\"01234567-890a-bcde-f012-34567890abc0\",\n" +
            "    \"device_type\":\"android\",\n" +
            "    \"installed\":true,\n" +
            "    \"opt_in\":false,\n" +
            "    \"push_address\":null,\n" +
            "    \"created\":\"2014-07-12T00:45:01\",\n" +
            "    \"last_registration\":\"2014-08-06T00:33:25\",\n" +
            "    \"alias\":null,\n" +
            "    \"tags\":[  \n" +
            "    ],\n" +
            "    \"tag_groups\": {\n" +
            "      \"tagGroup\": \n" +
            "        [\n" +
            "          \"tag1\",\n" +
            "          \"tag2\"\n" +
            "        ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

        assertEquals(listRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

    @Test
    public void testSmsUri() {
        String msisdn = "15552243311";
        String sender = "12345";
        ChannelRequest request = ChannelRequest.newSmsLookupRequest(msisdn, sender);

        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/channels/sms/" + msisdn + "/" + sender);

        assertEquals(expectedURI, request.getUri(baseURI));
    }

}
