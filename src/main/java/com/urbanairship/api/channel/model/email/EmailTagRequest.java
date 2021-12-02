package com.urbanairship.api.channel.model.email;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The EmailTagRequest class builds email tag mutation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}. Open Channel Tags Request will not function for Email.
 */
public class EmailTagRequest implements Request<String> {

    private final static String EMAIL_CHANNELS_TAGS_PATH = "/api/channels/email/tags";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";
    private static final String EMAIL_CHANNEL_KEY = "email_address";

    private final Map<String, Set<String>> addTags = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> removeTags = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> setTags = new HashMap<String, Set<String>>();

    private ObjectNode payloadNode = JsonNodeFactory.instance.objectNode();
    private ObjectNode emailNode = JsonNodeFactory.instance.objectNode();

    private StringBuilder jsonStringBuilder = new StringBuilder();

    /**
     * Create new Email Channel tag mutation request.
     *
     * @return EmailTagRequest
     */
    public static EmailTagRequest newRequest() {
        return new EmailTagRequest();
    }

    /**
     * Add an Email channel to the request audience.
     *
     * @param channel String
     * @return EmailTagRequest
     */
    public EmailTagRequest addEmailChannel(String channel) {
        jsonStringBuilder.append(channel);
        return this;
    }


    /**
     * Add tag group and tags to add to channels.
     *
     * @param tagGroup String
     * @param tags Set of Strings
     * @return ChannelTagRequest
     * */
    public EmailTagRequest addTags(String tagGroup, Set<String> tags) {
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
    public EmailTagRequest removeTags(String tagGroup, Set<String> tags) {
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
    public EmailTagRequest setTags(String tagGroup, Set<String> tags) {
        setTags.put(tagGroup, tags);
        return this;
    }


    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {

        emailNode.put(EMAIL_CHANNEL_KEY, jsonStringBuilder.toString());
        payloadNode.set(AUDIENCE_KEY, emailNode);

        if (!addTags.isEmpty()) {
            payloadNode.putPOJO(ADD_KEY,addTags);        }

        if (!removeTags.isEmpty()) {
            payloadNode.putPOJO(REMOVE_KEY,removeTags);        }

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
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, EMAIL_CHANNELS_TAGS_PATH);
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

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}