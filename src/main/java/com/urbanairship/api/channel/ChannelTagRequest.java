package com.urbanairship.api.channel;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChannelTagRequest implements Request<String> {

    private final static String API_CHANNELS_TAGS_PATH = "/api/channels/tags/";
    private static final String IOS_CHANNEL_KEY = "ios_channel";
    private static final String ANDROID_CHANNEL_KEY = "android_channel";
    private static final String AMAZON_CHANNEL_KEY = "amazon_channel";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";

    Map<String, Map<String, Set<String>>> payload = new HashMap<String, Map<String, Set<String>>>();
    Map<String, Set<String>> audience = new HashMap<String, Set<String>>();
    Map<String, Set<String>> addTags;
    Map<String, Set<String>> removeTags;
    Map<String, Set<String>> setTags;

    public static ChannelTagRequest createChannelsTagRequest() {
        return new ChannelTagRequest();
    }

    public ChannelTagRequest addIOSChannel(String channel) {
        return addIOSChannels(channel);
    }

    public ChannelTagRequest addIOSChannels(String... channels) {
        return addIOSChannels(new HashSet<String>(Arrays.asList(channels)));
    }

    public ChannelTagRequest addIOSChannels(Set<String> channels) {
        appendMapValues(IOS_CHANNEL_KEY, channels, this.audience);
        return this;
    }

    public ChannelTagRequest addAndroidChannel(String channel) {
        return addAndroidChannels(channel);
    }

    public ChannelTagRequest addAndroidChannels(String... channels) {
        return addAndroidChannels(new HashSet<String>(Arrays.asList(channels)));
    }

    public ChannelTagRequest addAndroidChannels(Set<String> channels) {
        appendMapValues(ANDROID_CHANNEL_KEY, channels, this.audience);
        return this;
    }

    public ChannelTagRequest addAmazonChannel(String channel) {
        return addAmazonChannels(channel);
    }

    public ChannelTagRequest addAmazonChannels(String... channels) {
        return addAmazonChannels(new HashSet<String>(Arrays.asList(channels)));
    }

    public ChannelTagRequest addAmazonChannels(Set<String> channels) {
        appendMapValues(AMAZON_CHANNEL_KEY, channels, this.audience);
        return this;
    }

    public ChannelTagRequest addTags(String tagGroup, Set<String> tags) {
        if (addTags == null) {
            addTags = new HashMap<String, Set<String>>();
        }
        appendMapValues(tagGroup, tags, this.addTags);
        return this;
    }

    public ChannelTagRequest removeTags(String tagGroup, Set<String> tags) {
        if (removeTags == null) {
            removeTags = new HashMap<String, Set<String>>();
        }
        appendMapValues(tagGroup, tags, this.removeTags);
        return this;
    }

    public ChannelTagRequest setTags(String tagGroup, Set<String> tags) {
        if (setTags == null) {
            setTags = new HashMap<String, Set<String>>();
        }
        appendMapValues(tagGroup, tags, this.setTags);
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

        payload.put(AUDIENCE_KEY, audience);
        if (addTags != null) {
            payload.put(ADD_KEY, addTags);
        }

        if (removeTags != null) {
            payload.put(REMOVE_KEY, removeTags);
        }

        if (setTags != null) {
            payload.put(SET_KEY, setTags);
        }

        Preconditions.checkArgument(payload.containsKey(AUDIENCE_KEY), "Audience required when executing a named user tag operation");
        Preconditions.checkArgument(payload.containsKey(ADD_KEY) || payload.containsKey(REMOVE_KEY) || payload.containsKey(SET_KEY), "Audience required when executing a named user tag operation");
        if (payload.containsKey(SET_KEY)) {
            Preconditions.checkArgument(!payload.containsKey(REMOVE_KEY) && !payload.containsKey(ADD_KEY));
        }

        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Payload parsing error.");
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_CHANNELS_TAGS_PATH);
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

    private static void appendMapValues(String key, Set<String> values, Map<String, Set<String>> map) {
        if (!map.containsKey(key)) {
            map.put(key, values);
        } else {
            Set<String> newSet = map.get(key);
            newSet.addAll(values);
            map.put(key, newSet);
        }

    }
}
