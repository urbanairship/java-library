package com.urbanairship.api.reports;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.PushDetailResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @deprecated Marked to be removed in 2.0.0. Urban Airship stopped recommending use of these endpoints in October 2015,
 * so we are now completing their removal from our libraries.
 */
@Deprecated
public class PushDetailRequest implements Request<List<PushDetailResponse>> {
    private final static String API_PER_PUSH_DETAIL = "/api/reports/perpush/detail/";
    private final static String PUSH_IDS_KEY = "push_ids";

    private final ImmutableSet.Builder<String> pushIds = new ImmutableSet.Builder<String>();

    /**
     * Create new push detail request.
     *
     * @return PushDetailRequest
     */
    public static PushDetailRequest newRequest() {
        return new PushDetailRequest();
    }

    /**
     * Add a push ID to the request list.
     *
     * @param pushId String
     * @return PerPushDetailRequest
     */
    public PushDetailRequest addPushId(String pushId) {
        return addPushIds(pushId);
    }

    /**
     * Add multiple push IDs to the request list.
     *
     * @param pushIds String... of push IDs
     * @return PerPushDetailRequest
     */
    public PushDetailRequest addPushIds(String... pushIds) {
        this.pushIds.addAll(Arrays.asList(pushIds));
        return this;
    }

    /**
     * Add a set of push IDs to the request audience.
     *
     * @param pushIds Set of push IDs
     * @return ChannelTagRequest
     */
    public PushDetailRequest addPushIds(Set<String> pushIds) {
        this.pushIds.addAll(pushIds);
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
        Preconditions.checkArgument(!pushIds.build().isEmpty(), "Push IDs when making a push detail request");

        Map<String, ImmutableSet<String>> payload = new HashMap<String, ImmutableSet<String>>();
        payload.put(PUSH_IDS_KEY, pushIds.build());

        try {
            return ReportsObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_PER_PUSH_DETAIL);
    }

    @Override
    public ResponseParser<List<PushDetailResponse>> getResponseParser() {
        return new ResponseParser<List<PushDetailResponse>>() {
            @Override
            public List<PushDetailResponse> parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, new TypeReference<List<PushDetailResponse>>() {});
            }
        };
    }
}
