package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The OpenChannelTagRequest class builds open channel tag mutation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class OpenChannelTagRequest implements Request<String> {

    private final static String OPEN_CHANNELS_TAGS_PATH = "/api/channels/open/tags";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";
    private static final String ADDRESS_KEY = "address";
    private static final String OPEN_PLATFORM_NAME_KEY = "open_platform_name";

    private final Map<String, Set<String>> addTags = new HashMap<>();
    private final Map<String, Set<String>> removeTags = new HashMap<>();
    private final Map<String, Set<String>> setTags = new HashMap<>();

    private ObjectNode payloadNode = JsonNodeFactory.instance.objectNode();
    private ObjectNode openNode = JsonNodeFactory.instance.objectNode();

    private String address;
    private String openPlatformName;

    /**
     * Create new Open Channel tag mutation request.
     *
     * @return OpenChannelTagRequest
     */
    public static OpenChannelTagRequest newRequest() {
        return new OpenChannelTagRequest();
    }

    /**
     * Add an Open channel to the request audience.
     *
     * @param address String
     * @param openPlatformName String
     * @return OpenChannelTagRequest
     */
    public OpenChannelTagRequest addOpenChannel(String address, String openPlatformName) {
        this.address = address;
        this.openPlatformName = openPlatformName;
        return this;
    }


    /**
     * Add tag group and tags to add to channels.
     *
     * @param tagGroup String
     * @param tags Set of Strings
     * @return ChannelTagRequest
     * */
    public OpenChannelTagRequest addTags(String tagGroup, Set<String> tags) {
        addTags.put(tagGroup, tags);
        return this;
    }

    /**
     * Add tag group and tags to remove from channels.
     *
     * @param tagGroup String
     * @param tags Set of Strings
     * @return ChannelTagRequest
     */
    public OpenChannelTagRequest removeTags(String tagGroup, Set<String> tags) {
        removeTags.put(tagGroup, tags);
        return this;
    }

    /**
     * Add tag group and tags to set to channels.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return ChannelTagRequest
     */
    public OpenChannelTagRequest setTags(String tagGroup, Set<String> tags) {
        setTags.put(tagGroup, tags);
        return this;
    }


    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {

        openNode.put(ADDRESS_KEY, address);
        openNode.put(OPEN_PLATFORM_NAME_KEY, openPlatformName);
        payloadNode.set(AUDIENCE_KEY, openNode);

        if (!addTags.isEmpty()) {
            payloadNode.putPOJO(ADD_KEY,addTags);        
        }

        if (!removeTags.isEmpty()) {
            payloadNode.putPOJO(REMOVE_KEY,removeTags);        
        }

        if (!setTags.isEmpty()) {
            payloadNode.putPOJO(SET_KEY, setTags);
        }

        Preconditions.checkArgument(payloadNode.has(ADD_KEY) || payloadNode.has(REMOVE_KEY) ||
                payloadNode.has(SET_KEY), "Audience required when executing a channel tag operation");
        if (payloadNode.has(SET_KEY)) {
            Preconditions.checkArgument(!payloadNode.has(REMOVE_KEY) && !payloadNode.has(ADD_KEY));
        }

        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payloadNode);
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
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, OPEN_CHANNELS_TAGS_PATH);
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