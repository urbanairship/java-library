package com.urbanairship.api.reports;

import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
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

    private final static String API_TIME_IN_APP = "/api/reports/timeinapp/";
    private final static String API_APP_OPENS = "/api/reports/opens/";
    private final static String API_OPT_INS = "/api/reports/optins/";
    private final static String API_OPT_OUTS = "/api/reports/optouts/";
    private final static String API_SENDS = "/api/reports/sends/";

    private final String path;
    private DateTime start;
    private DateTime end;
    private Precision precision;

    private PlatformStatsRequest(String path) {
        this.path = path;
    }

    public static PlatformStatsRequest newTimeInAppRequest() {
        return new PlatformStatsRequest(API_TIME_IN_APP);
    }

    public static PlatformStatsRequest newAppOpensRequest() {
        return new PlatformStatsRequest(API_APP_OPENS);
    }

    public static PlatformStatsRequest newOptInsRequest() {
        return new PlatformStatsRequest(API_OPT_INS);
    }

    public static PlatformStatsRequest newOptOutsRequest() {
        return new PlatformStatsRequest(API_OPT_OUTS);
    }

    public static PlatformStatsRequest newPushSendsRequest() {
        return new PlatformStatsRequest(API_SENDS);
    }

    /**
     * Set the request start date
     *
     * @return PlatformStatsRequest
     */
    public PlatformStatsRequest start(DateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Set the request end date
     *
     * @return PlatformStatsRequest
     */
    public PlatformStatsRequest end(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Set the request precision
     *
     * @return DateTime
     */
    public PlatformStatsRequest precision(Precision precision) {
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
        builder.addParameter("start", this.start.toString());
        builder.addParameter("end", this.end.toString());
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
