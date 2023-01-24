package com.urbanairship.api.sms;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.sms.parse.SmsObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The KeywordInteractionRequest class builds keyword interaction request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class KeywordInteractionRequest implements Request<String> {

    private final static String API_SMS = "/api/sms/";
    private final List<String> senderIds = new ArrayList<>();;

    private String keyword;
    private final String path;

    private ObjectNode payloadNode = JsonNodeFactory.instance.objectNode();

    private KeywordInteractionRequest(String path) {
        this.path = path;
    }

    /**
     * Create new KeywordInteractionRequest request.
     *
     * @param msisdn String
     * @return KeywordInteractionRequest
     */
    public static KeywordInteractionRequest newRequest(String msisdn) {
        return new KeywordInteractionRequest(API_SMS + msisdn + "/keywords");
    }

    /**
     * Add a keyword to the payload.
     *
     * @param keyword String
     * @return KeywordInteractionRequest
     */
    public KeywordInteractionRequest addKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    /**
     * Add a sender id to the list.
     *
     * @param senderId String
     * @return KeywordInteractionRequest
     */
    public KeywordInteractionRequest addSenderId(String senderId) {
        senderIds.add(senderId);
        return this;
    }

    /**
     * Add list of sender ids.
     * @param senderId List
     * 
     * @return KeywordInteractionRequest
     */
    public KeywordInteractionRequest addAllSenderIds(List<String> senderId) {
        senderIds.addAll(senderId);
        return this;
    }


    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {

        payloadNode.put("keyword", keyword);
        payloadNode.putPOJO("sender_ids", senderIds);

        try {
            return SmsObjectMapper.getInstance().writeValueAsString(payloadNode);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return response -> response;
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