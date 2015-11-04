package com.urbanairship.api.reports;

import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class StatisticsCsvRequest implements Request<String> {
    private final static String API_STATISTICS = "/api/push/stats/";

    private final DateTime start;
    private final DateTime end;

    private StatisticsCsvRequest() {
        this(null,null);
    }

    private StatisticsCsvRequest(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Create a statistics request with a csv response
     *
     * @param start DateTime
     * @param end DateTime
     */
    public static StatisticsCsvRequest newRequest(DateTime start, DateTime end) {
        return new StatisticsCsvRequest(start, end);
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
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.GET;
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
        builder.addParameter("start", this.start.toString());
        builder.addParameter("end", this.end.toString());
        builder.addParameter("format", "csv");

        return builder.build();
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

}
