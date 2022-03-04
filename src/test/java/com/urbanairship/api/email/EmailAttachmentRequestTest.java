package com.urbanairship.api.email;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.email.model.EmailAttachmentResponse;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EmailAttachmentRequestTest {
    private final static String EMAIL_ATTACHMENT_PATH = "/api/attachments";

    EmailAttachmentRequest attachmentRequest;

    @Before
    public void setupCreate() {
        attachmentRequest = EmailAttachmentRequest.newRequest()
                .setContentType("text/plain")
                .setData("kiBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWF")
                .setFilename("toto.png");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(attachmentRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(attachmentRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {

        EmailAttachmentRequest attachmentRequest2 = EmailAttachmentRequest.newRequest()
            .setContentType("text/plain")
            .setData("kiBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWF")
            .setFilename("toto.png");

        String attachmentString = "{" +
                "\"filename\":\"toto.png\"," +
                "\"content_type\":\"text/plain\"," +
                "\"data\":\"kiBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWF\"" +
                "}";

        assertEquals(attachmentRequest2.getRequestBody(), attachmentString);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(attachmentRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedCreateUri = URI.create("https://go.urbanairship.com" + EMAIL_ATTACHMENT_PATH);
        assertEquals(attachmentRequest.getUri(baseURI), expectedCreateUri);
    }
    
    @Test
    public void testCustomEventParser() throws Exception {

        EmailAttachmentResponse emailAttachmentResponse = new EmailAttachmentResponse(true, ImmutableList.of("df6a6b50-9843-0304-d5a5-743f246a8567"));

        String response = "{\"ok\" : true,\"attachment_ids\" : [\"df6a6b50-9843-0304-d5a5-743f246a8567\"]}";
        assertEquals(attachmentRequest.getResponseParser().parse(response), emailAttachmentResponse);
    }

}
