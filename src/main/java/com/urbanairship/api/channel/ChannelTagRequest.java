/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

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

/**
 * The ChannelTagRequest class builds channels tag mutation requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ChannelTagRequest implements Request<String> {

    private final static String API_CHANNELS_TAGS_PATH = "/api/channels/tags/";
    private static final String IOS_CHANNEL_KEY = "ios_channel";
    private static final String ANDROID_CHANNEL_KEY = "android_channel";
    private static final String AMAZON_CHANNEL_KEY = "amazon_channel";
    private static final String AUDIENCE_KEY = "audience";
    private static final String ADD_KEY = "add";
    private static final String REMOVE_KEY = "remove";
    private static final String SET_KEY = "set";

    private final Map<String, Set<String>> audience = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> addTags = new HashMap<String, Set<String>>();;
    private final Map<String, Set<String>> removeTags = new HashMap<String, Set<String>>();;
    private final Map<String, Set<String>> setTags = new HashMap<String, Set<String>>();;

    /**
     * Create new channels tag mutation request.
     *
     * @return ChannelTagRequest
     */
    public static ChannelTagRequest newRequest() {
        return new ChannelTagRequest();
    }

    /**
     * Add an iOS channel to the request audience.
     *
     * @param channel String
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addIOSChannel(String channel) {
        return addIOSChannels(channel);
    }

    /**
     * Add multiple iOS channels to the request audience.
     *
     * @param channels String... of channel IDs
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addIOSChannels(String... channels) {
        return addIOSChannels(new HashSet<String>(Arrays.asList(channels)));
    }

    /**
     * Add a set of iOS channels to the request audience.
     *
     * @param channels Set of channel IDs
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addIOSChannels(Set<String> channels) {
        appendMapValues(IOS_CHANNEL_KEY, channels, this.audience);
        return this;
    }
    /**
     * Add an DeviceStats channel to the request audience.
     *
     * @param channel String
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addAndroidChannel(String channel) {
        return addAndroidChannels(channel);
    }

    /**
     * Add multiple DeviceStats channels to the request audience.
     *
     * @param channels String... of channel IDs
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addAndroidChannels(String... channels) {
        return addAndroidChannels(new HashSet<String>(Arrays.asList(channels)));
    }

    /**
     * Add a set of DeviceStats channel to the request audience.
     *
     * @param channels Set of channels IDs
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addAndroidChannels(Set<String> channels) {
        appendMapValues(ANDROID_CHANNEL_KEY, channels, this.audience);
        return this;
    }

    /**
     * Add an Amazon channel to the request audience.
     *
     * @param channel String
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addAmazonChannel(String channel) {
        return addAmazonChannels(channel);
    }

    /**
     * Add multiple Amazon channels to the request audience.
     *
     * @param channels String... of channel IDs
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addAmazonChannels(String... channels) {
        return addAmazonChannels(new HashSet<String>(Arrays.asList(channels)));
    }

    /**
     * Add a set of Amazon channels to the request audience.
     *
     * @param channels Set of channel IDs
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addAmazonChannels(Set<String> channels) {
        appendMapValues(AMAZON_CHANNEL_KEY, channels, this.audience);
        return this;
    }

    /**
     * Add tag group and tags to add to channels.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return ChannelTagRequest
     */
    public ChannelTagRequest addTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.addTags);
        return this;
    }

    /**
     * Add tag group and tags to remove from channels.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return ChannelTagRequest
     */
    public ChannelTagRequest removeTags(String tagGroup, Set<String> tags) {
        appendMapValues(tagGroup, tags, this.removeTags);
        return this;
    }

    /**
     * Add tag group and tags to set to channels.
     *
     * @param tagGroup String
     * @param tags Set of tags
     * @return ChannelTagRequest
     */
    public ChannelTagRequest setTags(String tagGroup, Set<String> tags) {
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
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        final Map<String, Map<String, Set<String>>> payload = new HashMap<String, Map<String, Set<String>>>();

        payload.put(AUDIENCE_KEY, audience);
        if (!addTags.isEmpty()) {
            payload.put(ADD_KEY, addTags);
        }

        if (!removeTags.isEmpty()) {
            payload.put(REMOVE_KEY, removeTags);
        }

        if (!setTags.isEmpty()) {
            payload.put(SET_KEY, setTags);
        }

        Preconditions.checkArgument(payload.containsKey(AUDIENCE_KEY), "Audience required when executing a channel tag operation");
        Preconditions.checkArgument(payload.containsKey(ADD_KEY) || payload.containsKey(REMOVE_KEY) || payload.containsKey(SET_KEY), "Audience required when executing a channel tag operation");
        if (payload.containsKey(SET_KEY)) {
            Preconditions.checkArgument(!payload.containsKey(REMOVE_KEY) && !payload.containsKey(ADD_KEY));
        }

        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
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

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
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
