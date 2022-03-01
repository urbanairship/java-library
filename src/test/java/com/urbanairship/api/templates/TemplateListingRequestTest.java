package com.urbanairship.api.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.templates.model.TemplateListingResponse;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.HttpHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TemplateListingRequestTest {

    private final static ObjectMapper mapper = TemplatesObjectMapper.getInstance();
    private final static String templateName = "template123";
    private final static String LISTING_PATH = "/api/templates/";
    private final static String LOOKUP_PATH = LISTING_PATH + templateName;

    TemplateListingRequest lookupRequest;
    TemplateListingRequest listingRequest;

    @Before
    public void setup() {
        lookupRequest = TemplateListingRequest.newRequest(templateName);
        listingRequest = TemplateListingRequest.newRequest();
    }

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(lookupRequest.getContentType(), null);
        Assert.assertEquals(listingRequest.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(lookupRequest.getHttpMethod(), Request.HttpMethod.GET);
        Assert.assertEquals(listingRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(lookupRequest.getRequestBody(), null);
        Assert.assertEquals(listingRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        Assert.assertEquals(lookupRequest.getRequestHeaders(), headers);
        Assert.assertEquals(listingRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + LISTING_PATH);
        Assert.assertEquals(listingRequest.getUri(baseURI), expectedUri);

        expectedUri = URI.create("https://go.urbanairship.com" + LOOKUP_PATH);
        Assert.assertEquals(lookupRequest.getUri(baseURI), expectedUri);
    }

    @Test
    public void testResponseParser() throws Exception {
        String lookupJson =
                "{"+
                    "\"ok\" : true,"+
                    "\"template\": {"+
                        "\"id\" : \"ef34a8d9-0ad7-491c-86b0-aea74da15161\","+
                        "\"created_at\" : \"2015-08-17T11:10:02Z\","+
                        "\"modified_at\" : \"2015-08-17T11:10:02Z\","+
                        "\"last_used\" : \"2015-08-17T11:10:01Z\","+
                        "\"name\" : \"Welcome Message\","+
                        "\"description\": \"Our welcome message\","+
                        "\"variables\": ["+
                            "{"+
                                "\"key\": \"TITLE\","+
                                "\"name\": \"Title\","+
                                "\"description\": \"e.g. Mr, Ms, Dr, etc.\","+
                                "\"default_value\": \"\""+
                            "},"+
                            "{"+
                                "\"key\": \"FIRST_NAME\","+
                                "\"name\": \"First Name\","+
                                "\"description\": \"Given name\","+
                                "\"default_value\": null"+
                            "},"+
                            "{"+
                                "\"key\": \"LAST_NAME\","+
                                "\"name\": \"Last Name\","+
                                "\"description\": \"Family name\","+
                                "\"default_value\": null"+
                            "}"+
                        "],"+
                        "\"push\": {"+
                            "\"notification\": {"+
                                "\"alert\": \"Hello {{FIRST_NAME}}, this is your welcome message!\""+
                            "}"+
                        "}"+
                    "}"+
                "}";

        String listingJson =
                "{"+
                    "\"ok\" : true,"+
                    "\"count\": 1,"+
                    "\"total_count\": 2,"+
                    "\"templates\": ["+
                        "{"+
                            "\"id\" : \"ef34a8d9-0ad7-491c-86b0-aea74da15161\","+
                            "\"created_at\" : \"2015-08-17T11:10:01Z\","+
                            "\"modified_at\" : \"2015-08-17T11:10:01Z\","+
                            "\"last_used\" : \"2015-08-17T11:10:01Z\","+
                            "\"name\" : \"Welcome Message\","+
                            "\"description\": \"Our welcome message\","+
                            "\"variables\": ["+
                                "{"+
                                    "\"key\": \"TITLE\","+
                                    "\"name\": \"Title\","+
                                    "\"description\": \"e.g. Mr, Ms, Dr, etc.\","+
                                    "\"default_value\": \"\""+
                                "},"+
                                "{"+
                                    "\"key\": \"FIRST_NAME\","+
                                    "\"name\": \"First Name\","+
                                    "\"description\": \"Given name\","+
                                    "\"default_value\": \"test\""+
                                "},"+
                                "{"+
                                    "\"key\": \"LAST_NAME\","+
                                    "\"name\": \"Last Name\","+
                                    "\"description\": \"Family name\","+
                                    "\"default_value\": \"blah\""+
                                "}"+
                            "],"+
                            "\"push\": {"+
                                "\"notification\": {"+
                                    "\"alert\": \"Hello {{FIRST_NAME}}, this is your welcome message!\""+
                                "}"+
                            "}"+
                        "}"+
                    "],"+
                    "\"prev_page\": null,"+
                    "\"next_page\": \"https://go.urbanairship.com/api/templates?page=2&page_size=1\""+
                "}";



        final ResponseParser<TemplateListingResponse> responseParser = new ResponseParser<TemplateListingResponse>() {
            @Override
            public TemplateListingResponse parse(String response) throws IOException {
                return mapper.readValue(response, TemplateListingResponse.class);
            }
        };

        Assert.assertEquals(lookupRequest.getResponseParser().parse(lookupJson), responseParser.parse(lookupJson));
        Assert.assertEquals(listingRequest.getResponseParser().parse(listingJson), responseParser.parse(listingJson));
    }

}