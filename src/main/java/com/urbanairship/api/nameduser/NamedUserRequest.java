/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The NamedUserRequest class builds named user association and disassociation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class NamedUserRequest implements Request<String> {

    private final static String API_NAMED_USERS_ASSOCIATE = "/api/named_users/associate/";
    private final static String API_NAMED_USERS_DISASSOCIATE = "/api/named_users/disassociate/";

    private static final String CHANNEL_KEY = "channel_id";
    private static final String DEVICE_TYPE_KEY = "device_type";
    private static final String NAMED_USER_ID_KEY = "named_user_id";
    private static final String EMAIL_KEY = "email_address";

    private final String path;
    private final Map<String, String> payload = new HashMap<String, String>();

    private NamedUserRequest(String path) {
        this.path = path;
    }

    /**
     * Create a named user association request.
     *
     * @return NamedUserRequest
     */
    public static NamedUserRequest newAssociationRequest() {
        return new NamedUserRequest(API_NAMED_USERS_ASSOCIATE);
    }

    /**
     * Create a named user disassociation request.
     *
     * @return NamedUserRequest
     */
    public static NamedUserRequest newDisassociationRequest() {
        return new NamedUserRequest(API_NAMED_USERS_DISASSOCIATE);
    }

    /**
     * Set the request channel.
     *
     * @param channelId The channel ID as a string
     * @param channelType The channel platform as a ChannelType
     * @return NamedUserRequest
     */
    public NamedUserRequest setChannel(String channelId, ChannelType channelType) {
        payload.put(CHANNEL_KEY, channelId);

        if (channelType.equals(ChannelType.OPEN) || channelType.equals(ChannelType.WEB) ||
                channelType.equals(ChannelType.EMAIL) || channelType.equals(ChannelType.SMS)) {
            return this;
        }

        payload.put(DEVICE_TYPE_KEY, channelType.getIdentifier());
        return this;
    }

    /**
     * Set an email address to associate or disassociate.
     * The email must be registered to airship before an association can be made.
     *
     * @param email String
     * @return NamedUserRequest
     */
    public NamedUserRequest setEmail(String email) {
        payload.put(EMAIL_KEY, email);
        return this;
    }

    /**
     * Set the request named user ID - optional for disassociation requests.
     *
     * @param namedUserId String
     * @return NamedUserRequest
     */
    public NamedUserRequest setNamedUserId(String namedUserId) {
        payload.put(NAMED_USER_ID_KEY, namedUserId);
        return this;
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
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        Preconditions.checkArgument(!payload.isEmpty(), "Request payload cannot be empty");
        Preconditions.checkArgument(payload.containsKey(CHANNEL_KEY) || payload.containsKey(EMAIL_KEY), "Channel ID or email address is required for named user association or disassociation requests.");
        Preconditions.checkArgument(!(payload.containsKey(CHANNEL_KEY) && payload.containsKey(EMAIL_KEY)), "Both Channel ID and email cannot be set. Set either the channel ID or the email address.");

        if (path.equals(API_NAMED_USERS_ASSOCIATE)) {
            Preconditions.checkArgument(payload.containsKey(NAMED_USER_ID_KEY), "Named User ID required for named user association requests");
        }

        try {
            return NamedUserObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception e) {
            return "{ \"exception\" : \"" + e.getClass().getName() + "\", \"message\" : \"" + e.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

}