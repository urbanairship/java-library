package com.urbanairship.api.schedule;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListSchedulesRequest implements Request<ListAllSchedulesResponse> {

    private final String path;
    private final boolean isLookup;

    private ListSchedulesRequest(String path, boolean isLookup) {
        this.path = path;
        this.isLookup = isLookup;
    }


    public static ListSchedulesRequest newRequest() {
        return new ListSchedulesRequest(ScheduleRequest.API_SCHEDULE_PATH, false);
    }

    public static ListSchedulesRequest newRequest(String scheduleId) {
        return new ListSchedulesRequest(ScheduleRequest.API_SCHEDULE_PATH + scheduleId, true);
    }

    public static ListSchedulesRequest newRequest(UUID start, int limit, ListSchedulesOrderType orderType) {
        String path = ScheduleRequest.API_SCHEDULE_PATH + "?" + "start=" + start.toString() + "&limit=" + limit + "&order=" + orderType.getKey();
        return new ListSchedulesRequest(path, false);
    }

    public static ListSchedulesRequest newRequest(URI nextPage) {
        return new ListSchedulesRequest(nextPage.getPath() + "?" + nextPage.getQuery(), false);
    }

    @Override
    public HTTPMethod getHTTPMethod() {
        return HTTPMethod.GET;
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
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ListAllSchedulesResponse> getResponseParser() {
        final ObjectMapper mapper = ScheduleObjectMapper.getInstance();

        if (isLookup) {
            return new ResponseParser<ListAllSchedulesResponse>() {
                @Override
                public ListAllSchedulesResponse parse(String response) throws IOException {
                    return ListAllSchedulesResponse.newBuilder()
                        .setCount(1)
                        .setTotalCount(1)
                        .setOk(true)
                        .addSchedule(mapper.readValue(response, SchedulePayload.class))
                        .build();
                }
            };
        } else {
            return new ResponseParser<ListAllSchedulesResponse>() {
                @Override
                public ListAllSchedulesResponse parse(String response) throws IOException {
                    return mapper.readValue(response, ListAllSchedulesResponse.class);
                }
            };
        }
    }
}
