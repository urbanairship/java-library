package com.urbanairship.api.channel.model.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmailTagRequestPayload implements Request<String> {

    private final static String EMAIL_CHANNELS_TAGS_PATH = "/api/channels/email/tags";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";
    private static final String EMAIL_CHANNEL_KEY = "email_address";

    private final List<String> audience = new ArrayList<>();
    private final Map<String, Set<String>> addTags = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> removeTags = new HashMap<String, Set<String>>();

    @Override
    public HttpMethod getHttpMethod() {
        return null;
    }

    @Override
    public String getRequestBody() {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode payloadNode = factory.objectNode();
        ObjectNode audienceNode = factory.objectNode();

        for (String member: audience
        ) {
            audienceNode.put(EMAIL_CHANNEL_KEY, member);
        }
        payloadNode.set(AUDIENCE_KEY, audienceNode);
        try {
            System.out.println(mapper.writeValueAsString(payloadNode));
            return mapper.writeValueAsString(payloadNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return null;
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }
}
