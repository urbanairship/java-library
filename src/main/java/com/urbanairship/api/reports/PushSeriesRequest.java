package com.urbanairship.api.reports;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.model.PushSeriesResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PushSeriesRequest implements Request<PushSeriesResponse> {
    private final static String API_PER_PUSH_SERIES = "/api/reports/perpush/series/";

    private final String path;
    private Optional<DateTime> start;
    private Optional<DateTime> end;
    private Optional<Precision> precision;

    private PushSeriesRequest(String path) {
        this.path = path;
        this.start = Optional.absent();
        this.end = Optional.absent();
        this.precision = Optional.absent();
    }

    public static PushSeriesRequest newRequest(String pushId) {
        return new PushSeriesRequest(API_PER_PUSH_SERIES + pushId);
    }

    /**
     * Set the request start date
     *
     * @return PushSeriesRequest
     */
    public PushSeriesRequest setStart(DateTime start) {
        this.start = Optional.of(start);
        return this;
    }

    /**
     * Set the request end date
     *
     * @return PushSeriesRequest
     */
    public PushSeriesRequest setEnd(DateTime end) {
        this.end = Optional.of(end);
        return this;
    }

    /**
     * Set the request precision
     *
     * @return PushSeriesRequest
     */
    public PushSeriesRequest setPrecision(Precision precision) {
        this.precision = Optional.of(precision);
        return this;
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
        return headers;
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
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));

        if (precision.isPresent()) {
            builder.addParameter("precision", this.precision.get().toString());
        }

        if (start.isPresent()) {
            Preconditions.checkArgument(this.precision.isPresent(), "precision must be present if start is included");
            builder.addParameter("start", this.start.get().toString());
            if (end.isPresent()) {
                Preconditions.checkArgument(this.end.get().isAfter(this.start.get()), "end date must occur after start date");
                builder.addParameter("end", this.end.get().toString());
            }
        }

        return builder.build();
    }

    @Override
    public ResponseParser<PushSeriesResponse> getResponseParser() {
        return new ResponseParser<PushSeriesResponse>() {
            @Override
            public PushSeriesResponse parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, PushSeriesResponse.class);
            }
        };
    }
}
