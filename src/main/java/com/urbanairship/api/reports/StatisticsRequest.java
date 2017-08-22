package com.urbanairship.api.reports;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.StatisticsResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class StatisticsRequest implements Request<List<StatisticsResponse>> {
    private final static String API_STATISTICS = "/api/push/stats/";

    private final DateTime start;
    private final DateTime end;

    private StatisticsRequest() {
        this(null,null);
    }

    private StatisticsRequest(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Initialize a statistics request with json response
     *
     * @param start DateTime
     * @param end DateTime
     */
    public static StatisticsRequest newRequest(DateTime start, DateTime end) {
        return new StatisticsRequest(start, end);
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
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        Preconditions.checkNotNull(start, "start cannot be null");
        Preconditions.checkNotNull(end, "end cannot be null");
        Preconditions.checkArgument(end.isAfter(start), "end must occur after start");

        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, API_STATISTICS));
        builder.addParameter("start", this.start.toString(DateFormats.DATE_FORMATTER));
        builder.addParameter("end", this.end.toString(DateFormats.DATE_FORMATTER));

        return builder.build();
    }

    @Override
    public ResponseParser<List<StatisticsResponse>> getResponseParser() {
        return new ResponseParser<List<StatisticsResponse>>() {
            @Override
            public List<StatisticsResponse> parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, new TypeReference<List<StatisticsResponse>>() {});
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

}
