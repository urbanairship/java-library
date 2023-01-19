/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The ChannelRequest class builds channel listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ChannelRequest implements Request<ChannelResponse> {

    private final static String API_CHANNELS_LIST = "/api/channels/";
    private final static String API_SMS_CHANNEL = "/api/channels/sms/";
    private final static String API_EMAIL_CHANNEL = "/api/channels/email/";


    private final String path;

    private ChannelRequest(String path) {
        this.path = path;
    }

    /**
     * Create new request for single channel lookup.
     *
     * @param channel String
     * @return ChannelRequest
     */
    public static ChannelRequest newRequest(String channel) {
        return new ChannelRequest(API_CHANNELS_LIST + channel);
    }

    /**
     * Create new request for channel listing.
     *
     * @return ChannelRequest
     */
    public static ChannelRequest newRequest() {
        return new ChannelRequest(API_CHANNELS_LIST);
    }

    /**
     * Create a request for channel listing using a next page URI.
     *
     * @param nextPage URI
     * @return ChannelRequest
     */
    public static ChannelRequest newRequest(URI nextPage) {
        Preconditions.checkNotNull(nextPage, "Next page URI cannot be null");
        return new ChannelRequest(nextPage.getPath() + "?" + nextPage.getQuery());
    }

    /**
     * Create a request for looking up an sms channel.
     *
     * @param msisdn The mobile phone number you want to look up. Must be numerical characters with no leading zeros.
     * @param sender The SMS sender ID provided by Urban Airship. Must be numeric characters only.
     * @return ChannelRequest
     */
    public static ChannelRequest newSmsLookupRequest(String msisdn, String sender) {
        Preconditions.checkNotNull(msisdn, "msisdn cannot be null.");
        Preconditions.checkNotNull(sender, "Sender cannot be null.");

        return new ChannelRequest(API_SMS_CHANNEL + msisdn + "/" + sender);
    }

    /**
     * Create a request for looking up an email channel.
     *
     * @param email The mobile phone number you want to look up. Must be numerical characters with no leading zeros.
     * @return ChannelRequest
     */
    public static ChannelRequest newEmailLookupRequest(String email) {
        Preconditions.checkNotNull(email, "email cannot be null.");

        return new ChannelRequest(API_EMAIL_CHANNEL + email);
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
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ChannelResponse> getResponseParser() {
        return response -> ChannelObjectMapper.getInstance().readValue(response, ChannelResponse.class);
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
