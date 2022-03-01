package com.urbanairship.api.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TemplateDeleteRequestTest {

    private final static ObjectMapper mapper = TemplatesObjectMapper.getInstance();
    private final static String TEMPLATE_DELETE_PATH = "/api/templates/abc123";

    TemplateDeleteRequest request;

    @Before
    public void setup() {
        request = TemplateDeleteRequest.newRequest("abc123");
    }

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(request.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(request.getHttpMethod(), Request.HttpMethod.DELETE);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(request.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        Assert.assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + TEMPLATE_DELETE_PATH);
        Assert.assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testResponseParser() throws Exception {

        String responseJson = "{" +
                "\"ok\": true," +
                "\"operation_id\": \"a6394ff8-8a65-4494-ad06-677eb8b7ad6a\"" +
                "}";

        final ResponseParser<TemplateResponse> responseParser = new ResponseParser<TemplateResponse>() {
            @Override
            public TemplateResponse parse(String response) throws IOException {
                return mapper.readValue(response, TemplateResponse.class);
            }
        };

        Assert.assertEquals(request.getResponseParser().parse(responseJson), responseParser.parse(responseJson));
    }

}
