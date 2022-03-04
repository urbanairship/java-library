/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.email.model.EmailAttachmentResponse;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The EmailAttachmentRequest class builds an email attachment request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class EmailAttachmentRequest implements Request<EmailAttachmentResponse> {
    private final static String EMAIL_ATTACHMENT_PATH = "/api/attachments";

    private static final String FILENAME_KEY = "filename";
    private static final String CONTENT_TYPE_KEY = "content_type";
    private static final String DATA_KEY = "data";
    private final Map<String, Object> payload = new HashMap<>();

    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new GuavaModule());
    }

    /**
     * Build an email attachment request.
     *
     * @return EmailAttachmentRequest
     */
    public static EmailAttachmentRequest newRequest() {
        return new EmailAttachmentRequest();
    }

    /**
     * Set the filename of the email attachment.
     *
     * @param filename String
     * @return EmailAttachmentRequest
     */
    public EmailAttachmentRequest setFilename(String filename) {
        this.payload.put(FILENAME_KEY, filename);
        return this;
    }

    /**
     * Set the contentType of the email attachment.
     *
     * @param contentType String
     * @return EmailAttachmentRequest
     */
    public EmailAttachmentRequest setContentType(String contentType) {
        this.payload.put(CONTENT_TYPE_KEY, contentType);
        return this;
    }

    /**
     * Set the data of the email attachment.
     *
     * @param data String
     * @return EmailAttachmentRequest
     */
    public EmailAttachmentRequest setData(String data) {
        this.payload.put(DATA_KEY, data);
        return this;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        try {
            return mapper.writeValueAsString(this.payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, EMAIL_ATTACHMENT_PATH);
    }

    @Override
    public ResponseParser<EmailAttachmentResponse> getResponseParser() {
        return response -> mapper.readValue(response, EmailAttachmentResponse.class);
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }

}
