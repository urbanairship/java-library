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

public class NamedUserRequest implements Request<String> {

    private final static String API_NAMED_USERS_ASSOCIATE = "/api/named_users/associate";
    private final static String API_NAMED_USERS_DISASSOCIATE = "/api/named_users/disassociate";

    private static final String CHANNEL_KEY = "channel_id";
    private static final String DEVICE_TYPE_KEY = "device_type";
    private static final String NAMED_USER_ID_KEY = "named_user_id";

    private final String path;
    private final Map<String, String> payload = new HashMap<String, String>();

    private NamedUserRequest(String path) {
        this.path = path;
    }

    public static NamedUserRequest createAssociationRequest() {
        return new NamedUserRequest(API_NAMED_USERS_ASSOCIATE);
    }

    public static NamedUserRequest createDisassociationRequest() {
        return new NamedUserRequest(API_NAMED_USERS_DISASSOCIATE);
    }

    public NamedUserRequest setChannelId(String channelId) {
        payload.put(CHANNEL_KEY, channelId);
        return this;
    }

    public NamedUserRequest setNamedUserid(String namedUserId) {
        payload.put(NAMED_USER_ID_KEY, namedUserId);
        return this;
    }

    public NamedUserRequest setDeviceType(ChannelType deviceType) {
        payload.put(DEVICE_TYPE_KEY, deviceType.getIdentifier());
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
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        Preconditions.checkArgument(!payload.isEmpty(), "Request payload cannot be empty");
        Preconditions.checkArgument(payload.containsKey(CHANNEL_KEY), "Channel ID required for named user association or disassociation requests");
        Preconditions.checkArgument(payload.containsKey(DEVICE_TYPE_KEY), "Device type required for named user association or disassociation requests");

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

}