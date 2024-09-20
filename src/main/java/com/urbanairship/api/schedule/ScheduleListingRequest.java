package com.urbanairship.api.schedule;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The ListSchedulesRequest class builds schedule listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ScheduleListingRequest implements Request<ListAllSchedulesResponse> {

    private static final ResponseParser<ListAllSchedulesResponse> LIST_PARSER = response -> ScheduleObjectMapper.getInstance().readValue(response, ListAllSchedulesResponse.class);

    private static final ResponseParser<ListAllSchedulesResponse> SINGLE_LOOKUP_PARSER = response -> ListAllSchedulesResponse.newBuilder()
        .setCount(1)
        .setTotalCount(1)
        .setOk(true)
        .addSchedule(ScheduleObjectMapper.getInstance().readValue(response, SchedulePayloadResponse.class))
        .build();

    private final String path;
    private final ResponseParser<ListAllSchedulesResponse> parser;

    private ScheduleListingRequest(String path, ResponseParser<ListAllSchedulesResponse> parser) {
        this.path = path;
        this.parser = parser;
    }

    /**
     * Create a request to list all scheduled pushes.
     *
     * @return ListSchedulesRequest
     */
    public static ScheduleListingRequest newRequest() {
        return new ScheduleListingRequest(ScheduleRequest.API_SCHEDULE_PATH, LIST_PARSER);
    }

    /**
     * Create a request to lookup a specific scheduled push.
     *
     * @param scheduleId String
     * @return ListSchedulesRequest
     */
    public static ScheduleListingRequest newRequest(String scheduleId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "Schedule ID may not be null");
        return new ScheduleListingRequest(ScheduleRequest.API_SCHEDULE_PATH + scheduleId, SINGLE_LOOKUP_PARSER);
    }

    /**
     * Create a request to list scheduled pushes with search parameters.
     *
     * @param start UUID - The starting scheduled ID.
     * @param limit int - The page limit.
     * @param orderType ListSchedulesOrderType - How to order the response listing.
     * @return ListSchedulesRequest
     */
    public static ScheduleListingRequest newRequest(UUID start, int limit, ListSchedulesOrderType orderType) {
        Preconditions.checkNotNull(start, "Start ID may not be null");
        Preconditions.checkNotNull(orderType, "Listing response order option may not be null");
        String path = ScheduleRequest.API_SCHEDULE_PATH + "?" + "start=" + start.toString() + "&limit=" + limit + "&order=" + orderType.getKey();
        return new ScheduleListingRequest(path, LIST_PARSER);
    }


    /**
     * Create a request to lookup a next page of scheduled push listings.
     *
     * @param nextPage URI - The next page given by a listing response.
     * @return ListSchedulesRequest
     */
    public static ScheduleListingRequest newRequest(URI nextPage) {
        Preconditions.checkNotNull(nextPage, "Next page URI may not be null");
        return new ScheduleListingRequest(nextPage.getPath() + "?" + nextPage.getQuery(), LIST_PARSER);
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
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ListAllSchedulesResponse> getResponseParser() {
        return parser;
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
