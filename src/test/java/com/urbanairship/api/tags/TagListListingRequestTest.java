package com.urbanairship.api.tags;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.tags.model.TagListListingResponse;
import com.urbanairship.api.tags.model.TagListView;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TagListListingRequestTest {
    private final static String TAG_LIST_LISTING_PATH = "/api/tag-lists";

    TagListListingRequest request;

    @Before
    public void setup() {
        request = TagListListingRequest.newRequest();
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + TAG_LIST_LISTING_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testParser() throws Exception {

        TagListView tagListView1 = TagListView.newBuilder()
            .setName("platinum_members")
            .setDescription("loyalty program platinum members")
            .addExtra("key", "value")
            .addAddTags(ImmutableMap.of("test",ImmutableList.of("tptp","totot")))
            .addRemoveTags(ImmutableMap.of("test",ImmutableList.of("tptp","totot")))
            .addSetTags(ImmutableMap.of("test",ImmutableList.of("tptp","totot")))
            .setCreated(DateTime.parse("2013-01-24T23:55:05.000Z"))
            .setLastUpdated(DateTime.parse("2013-01-24T23:55:05.000Z"))
            .setChannelCount(3145)
            .setMutationSuccessCount(1)
            .setMutationErrorCount(0)
            .setErrorPath("")
            .setStatus("ready")
            .build();

        TagListView tagListView2 = TagListView.newBuilder()
            .setName("silver_members")
            .setDescription("loyalty program silver members")
            .addExtra("key2", "value2")
            .addAddTags(ImmutableMap.of("test",ImmutableList.of("tptp","totot")))
            .addRemoveTags(ImmutableMap.of("test",ImmutableList.of("tptp","totot")))
            .addSetTags(ImmutableMap.of("test",ImmutableList.of("tptp","totot")))
            .setCreated(DateTime.parse("2013-01-24T23:55:05.000Z"))
            .setLastUpdated(DateTime.parse("2013-01-24T23:55:05.000Z"))
            .setChannelCount(19999)
            .setMutationSuccessCount(1)
            .setMutationErrorCount(0)
            .setErrorPath("")
            .setStatus("ready")
            .build();

            TagListListingResponse tagListListingResponse = TagListListingResponse.newBuilder()
            .setOk(true)
            .addTagList(tagListView1)
            .addTagList(tagListView2)
            .build();

        String responseString = "{" +
                "\"ok\": true,\n" +
                "\"lists\": [\n" +
                    "{\n" +
                        "\"name\": \"platinum_members\",\n" +
                        "\"description\": \"loyalty program platinum members\",\n" +
                        "\"extra\": {\"key\": \"value\"},\n" +
                        "\"add\":{" +
                        "\"test\":[\"tptp\",\"totot\"]" +
                        "}," +
                        "\"remove\":{" +
                        "\"test\":[\"tptp\",\"totot\"]" +
                        "}," +
                        "\"set\":{" +
                        "\"test\":[\"tptp\",\"totot\"]" +
                        "}," +
                        "\"created\": \"2013-01-24T23:55:05\",\n" +
                        "\"last_updated\": \"2013-01-24T23:55:05\",\n" +
                        "\"channel_count\": 3145,\n" +
                        "\"mutation_success_count\": 1,\n" +
                        "\"mutation_error_count\": 0,\n" +
                        "\"error_path\": \"\",\n" +
                        "\"status\": \"ready\"\n" +
                    "},\n" +
                    "{\n" +
                        "\"name\": \"silver_members\",\n" +
                        "\"description\": \"loyalty program silver members\",\n" +
                        "\"extra\": {\"key2\": \"value2\"},\n" +
                        "\"add\":{" +
                        "\"test\":[\"tptp\",\"totot\"]" +
                        "}," +
                        "\"remove\":{" +
                        "\"test\":[\"tptp\",\"totot\"]" +
                        "}," +
                        "\"set\":{" +
                        "\"test\":[\"tptp\",\"totot\"]" +
                        "}," +
                        "\"created\": \"2013-01-24T23:55:05\",\n" +
                        "\"last_updated\": \"2013-01-24T23:55:05\",\n" +
                        "\"channel_count\": 19999,\n" +
                        "\"mutation_success_count\": 1,\n" +
                        "\"mutation_error_count\": 0,\n" +
                        "\"error_path\": \"\",\n" +
                        "\"status\": \"ready\"\n" +
                    "}\n" +
                "]\n" +
                "}";


        assertEquals(request.getResponseParser().parse(responseString), tagListListingResponse);
    }
}
