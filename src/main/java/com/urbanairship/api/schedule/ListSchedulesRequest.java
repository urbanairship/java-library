package com.urbanairship.api.schedule;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class ListSchedulesRequest implements Request<ListAllSchedulesResponse> {

    private final String path;

    private ListSchedulesRequest(String path) {
        this.path = path;
    }


    public static ListSchedulesRequest newRequest() {
        return new ListSchedulesRequest(ScheduleRequest.API_SCHEDULE_PATH);
    }

    public static ListSchedulesRequest newRequest(String scheduleId) {
        return new ListSchedulesRequest(ScheduleRequest.API_SCHEDULE_PATH + scheduleId);
    }

    public static ListSchedulesRequest newRequest(String start, int limit, String order) {
        String path = ScheduleRequest.API_SCHEDULE_PATH + "?" + "start=" + start + "&limit=" + limit + "&order=" + order;
        return new ListSchedulesRequest(path);
    }

    public static ListSchedulesRequest newRequest(URI nextPage) {
        return new ListSchedulesRequest(nextPage.getPath() + "?" + nextPage.getQuery());
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
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ListAllSchedulesResponse> getResponseParser() {
        return new ResponseParser<ListAllSchedulesResponse>() {
            @Override
            public ListAllSchedulesResponse parse(String response) throws IOException {
                return PushObjectMapper.getInstance().readValue(response, ListAllSchedulesResponse.class);
            }
        };    }
}
