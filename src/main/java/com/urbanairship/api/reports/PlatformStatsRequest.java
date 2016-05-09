package com.urbanairship.api.reports;

import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import com.urbanairship.api.reports.model.Precision;
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

/**
 * The PlatformStatsRequest class builds a request to one of the platform stats
 * endpoints -- app opens, time in app, opt ins, opt outs, or sends -- to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class PlatformStatsRequest implements Request<PlatformStatsResponse> {
    private final String path;
    private DateTime start;
    private DateTime end;
    private Precision precision;

    private PlatformStatsRequest(String path) {
        this.path = path;
    }

    /**
     * Create a platform stats request.
     *
     * @return PlatformStatsRequest
     */
    public static PlatformStatsRequest newRequest(PlatformStatsRequestType type) {
        return new PlatformStatsRequest(type.getPath());
    }

    /**
     * Set the request start date
     *
     * @return PlatformStatsRequest
     */
    public PlatformStatsRequest setStart(DateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Set the request end date
     *
     * @return PlatformStatsRequest
     */
    public PlatformStatsRequest setEnd(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Set the request precision
     *
     * @return DateTime
     */
    public PlatformStatsRequest setPrecision(Precision precision) {
        this.precision = precision;
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
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        Preconditions.checkNotNull(start, "start cannot be null");
        Preconditions.checkNotNull(end, "end cannot be null");
        Preconditions.checkNotNull(precision, "precision cannot be null");
        Preconditions.checkArgument(end.isAfter(start), "end date must occur after start date");

        URI uri;
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));
        builder.addParameter("start", this.start.toString(DateFormats.DATE_FORMATTER));
        builder.addParameter("end", this.end.toString(DateFormats.DATE_FORMATTER));
        builder.addParameter("precision", this.precision.toString());

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }

    @Override
    public ResponseParser<PlatformStatsResponse> getResponseParser() {
        return new ResponseParser<PlatformStatsResponse>() {
            @Override
            public PlatformStatsResponse parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, PlatformStatsResponse.class);
            }
        };
    }
}
