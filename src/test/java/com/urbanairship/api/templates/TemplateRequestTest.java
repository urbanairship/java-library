package com.urbanairship.api.templates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.templates.model.PartialPushPayload;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TemplateRequestTest {
    private final static ObjectMapper mapper = TemplatesObjectMapper.getInstance().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private final static String CREATE_UPDATE_TEMPLATE = "/api/templates/";
    private final static String TEMPLATE_NAME = "abc123";

    TemplateRequest createRequest;
    TemplateRequest updateRequest;
    String jsonPayload;

    @Before
    public void setup() throws Exception {
        TemplateVariable titleVariable = TemplateVariable.newBuilder()
                .setKey("TITLE")
                .setName("Title")
                .setDescription("e.g. Mr, Ms, Dr, etc")
                .setDefaultValue("")
                .build();

        TemplateVariable firstNameVariable = TemplateVariable.newBuilder()
                .setKey("FIRST_NAME")
                .setName("First Name")
                .setDescription("Given name")
                .setDefaultValue("Alex")
                .build();

        TemplateVariable lastNameVariable = TemplateVariable.newBuilder()
                .setKey("LAST_NAME")
                .setName("Last Name")
                .setDescription("Family name")
                .setDefaultValue("Smith")
                .build();

        PartialPushPayload partialPushPayload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                                .setAlert("Hello {{TITLE}} {{FIRST_NAME}} {{LAST_NAME}}, this is a test!")
                                .build()
                )
                .build();

        TemplateView templateView = TemplateView.newBuilder()
                .setName("Test Create")
                .setDescription("Description")
                .addVariable(titleVariable)
                .addVariable(firstNameVariable)
                .addVariable(lastNameVariable)
                .setPushPayload(partialPushPayload)
                .build();

        jsonPayload = mapper.writeValueAsString(templateView);

        createRequest = TemplateRequest.newRequest()
            .setName("Test Create")
            .setDescription("Description")
            .addVariable(titleVariable)
            .addVariable(firstNameVariable)
            .addVariable(lastNameVariable)
            .setPush(partialPushPayload);

        updateRequest = TemplateRequest.newRequest(TEMPLATE_NAME)
            .setPush(partialPushPayload);
    }

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(createRequest.getContentType(), ContentType.APPLICATION_JSON);
        Assert.assertEquals(updateRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(createRequest.getHttpMethod(), Request.HttpMethod.POST);
        Assert.assertEquals(updateRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        Assert.assertEquals(createRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(createRequest.getRequestBody(), jsonPayload);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + CREATE_UPDATE_TEMPLATE);
        Assert.assertEquals(createRequest.getUri(baseURI), expectedUri);

        expectedUri = URI.create("https://go.urbanairship.com" + CREATE_UPDATE_TEMPLATE + TEMPLATE_NAME);
        Assert.assertEquals(updateRequest.getUri(baseURI), expectedUri);
    }

    @Test
    public void testResponseParser() throws Exception {
        final ResponseParser responseParser = new ResponseParser<TemplateResponse>() {
            @Override
            public TemplateResponse parse(String response) throws IOException {
                return mapper.readValue(response, TemplateResponse.class);
            }
        };

        String createJson =
                "{" +
                    "\"ok\" : true," +
                    "\"operation_id\": \"9ce808c8-7176-45dc-b79e-44aa74249a5a\"," +
                    "\"template_id\" : \"ef34a8d9-0ad7-491c-86b0-aea74da15161\"" +
                "}";

        String updateJson =
                "{" +
                    "\"ok\" : true," +
                    "\"operation_id\": \"9ce808c8-7176-45dc-b79e-44aa74249a5a\"" +
                "}";


        Assert.assertEquals(createRequest.getResponseParser().parse(createJson), responseParser.parse(createJson));
        Assert.assertEquals(updateRequest.getResponseParser().parse(updateJson), responseParser.parse(updateJson));
    }

}