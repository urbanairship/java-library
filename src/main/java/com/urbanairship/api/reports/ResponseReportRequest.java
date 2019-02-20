package com.urbanairship.api.reports;

import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.model.Report;
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
 * The ResponseReportRequest class builds a request to the response report api
 * endpoint to be executed in the {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class ResponseReportRequest implements Request<Report> {
    private final String path;
    private final boolean nextPageRequest;
    private DateTime start;
    private DateTime end;
    private Precision precision;

    private ResponseReportRequest(String path, boolean nextPageRequest) {
        this.path = path;
        this.nextPageRequest = nextPageRequest;
    }

    /**
     * Create a Response Report request.
     *
     * @param uri String
     * @return ResponseReportRequest
     */
    public static ResponseReportRequest newRequest(String uri) {
        return new ResponseReportRequest(uri, false);
    }

    /**
     * Create a new Response Report request using a next page URI.
     *
     * @param nextPage URI
     * @return ResponseReportRequest
     */
    public static ResponseReportRequest newRequest(URI nextPage) {
        Preconditions.checkNotNull(nextPage, "Next page URI cannot be null");
        return new ResponseReportRequest(nextPage.getPath() + "?" + nextPage.getQuery(), true);
    }

    /**
     * Set the request start date
     *
     * @param start DateTime
     * @return ResponseReportRequest
     */
    public ResponseReportRequest setStart(DateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Set the request end date
     *
     * @param end DateTime
     * @return ResponseReportRequest
     */
    public ResponseReportRequest setEnd(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Set the request precision
     *
     * @param precision Precision
     * @return ResponseReportRequest
     */
    public ResponseReportRequest setPrecision(Precision precision) {
        this.precision = precision;
        return this;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
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
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) {
        URI uri;
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));

        if (!nextPageRequest) {
            Preconditions.checkNotNull(start, "start cannot be null");
            Preconditions.checkNotNull(end, "end cannot be null");
            Preconditions.checkNotNull(precision, "precision cannot be null");
            Preconditions.checkArgument(end.isAfter(start), "end date must occur after start date");

            builder.addParameter("start", this.start.toString(DateFormats.DATE_FORMATTER));
            builder.addParameter("end", this.end.toString(DateFormats.DATE_FORMATTER));
            builder.addParameter("precision", this.precision.toString());
        }

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }

    @Override
    public ResponseParser<Report> getResponseParser() {
        return new ResponseParser<Report>() {
            @Override
            public Report parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, Report.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() { return false; }
}
