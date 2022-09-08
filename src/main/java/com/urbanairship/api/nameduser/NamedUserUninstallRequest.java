package com.urbanairship.api.nameduser;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The NamedUserUninstallRequest class builds named users uninstall requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class NamedUserUninstallRequest implements Request<GenericResponse> {

    private final static String API_NAMED_USERS_UNINSTALL_PATH = "/api/named_users/uninstall/";
    private final Map<String, ImmutableList<String>> payload = new HashMap<String, ImmutableList<String>>();

    private NamedUserUninstallRequest(ImmutableList<String> namedUsersList) {
        payload.put(Constants.NAMED_USER, namedUsersList);
    }

    /**
     * Create new named users uninstall request.
     *
     * @param namedUsersList ImmutableList of strings.
     * @return NamedUserUninstallRequest
     */
    public static NamedUserUninstallRequest newUninstallRequest(ImmutableList<String> namedUsersList) {
        return new NamedUserUninstallRequest(namedUsersList);
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
        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_NAMED_USERS_UNINSTALL_PATH);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return RequestUtils.GENERIC_RESPONSE_PARSER;
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return false;
    }
}