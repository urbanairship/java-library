package com.urbanairship.api.attributelists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.attributelists.model.AttributeListsListingResponse;
import com.urbanairship.api.attributelists.parse.AttributeListsObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AttributeListsListingRequestTest {
    private final static ObjectMapper mapper = AttributeListsObjectMapper.getInstance();
    private final static String ATTRIBUTE_LISTS_LISTING_PATH = "/api/attribute-lists/";

    AttributeListsListingRequest request;

    @Before
    public void setup() {
        request = AttributeListsListingRequest.newRequest();
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

        URI expectedUri = URI.create("https://go.urbanairship.com" + ATTRIBUTE_LISTS_LISTING_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<AttributeListsListingResponse>() {
            @Override
            public AttributeListsListingResponse parse(String response) throws IOException {
                return mapper.readValue(response, AttributeListsListingResponse.class);
            }
        };

        String responseString = "{" +
                "\"ok\": true,\n" +
                "\"lists\": [\n" +
                    "{\n" +
                        "\"name\": \"ua_attributes_platinum_members\",\n" +
                        "\"description\": \"loyalty program platinum members\",\n" +
                        "\"extra\": {\"filename\": \"list.csv\",\"source\": \"crm\"},\n" +
                        "\"created\": \"2013-08-08T20:41:06\",\n" +
                        "\"last_updated\": \"2014-05-01T18:00:27\",\n" +
                        "\"channel_count\": 3145,\n" +
                        "\"error_path\": \"https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors\",\n" +
                        "\"status\": \"ready\"\n" +
                    "},\n" +
                    "{\n" +
                        "\"name\": \"ua_attributes_silver_members\",\n" +
                        "\"description\": \"loyalty program silver members\",\n" +
                        "\"extra\": {\"filename\": \"list2.csv\",\"source\": \"api\"},\n" +
                        "\"created\": \"2013-08-08T20:41:06\",\n" +
                        "\"last_updated\": \"2014-05-01T18:00:27\",\n" +
                        "\"channel_count\": 19999,\n" +
                        "\"error_path\": \"https://go.urbanairship.com/api/attribute-lists/ua_attributes_another_list/errors\",\n" +
                        "\"status\": \"ready\"\n" +
                    "},\n" +
                    "{\n" +
                        "\"name\": \"ua_attributes_gold_members\",\n" +
                        "\"description\": \"loyalty program gold members\",\n" +
                        "\"extra\": {\"filename\": \"list3.csv\",\"source\": \"api\"},\n" +
                        "\"created\": \"2013-08-08T20:45:06\",\n" +
                        "\"last_updated\": \"2015-05-01T18:00:27\",\n" +
                        "\"channel_count\": 2142,\n" +
                        "\"error_path\": \"https://go.urbanairship.com/api/attribute-lists/ua_attributes_another_list2/errors\",\n" +
                        "\"status\": \"processing\"\n" +
                    "}\n" +
                "]\n" +
                "}";


        assertEquals(request.getResponseParser().parse(responseString), responseParser.parse(responseString));
    }
}
