package com.urbanairship.api.reports;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.DevicesReport;
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
import java.util.Optional;

/**
 * The DevicesReportRequest class builds a request to the devices report api
 * endpoint to be executed in the {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class DevicesReportRequest implements Request<DevicesReport> {
    private final static String API_DEVICES_REPORT = "/api/reports/devices/";
    private final String path;
    private Optional<DateTime> date;

    private DevicesReportRequest(String path) {
        this.path = path;
        this.date = Optional.empty();
    }

    /**
     * Create a Devices Report request.
     *
     * @return DevicesReportRequest
     */
    public static DevicesReportRequest newRequest() {
        return new DevicesReportRequest(API_DEVICES_REPORT);
    }

    /**
     * Sets if the date is specified
     *
     * @param date DateTime object (is optional)
     * @return The DevicesReportRequest.
     */
    public DevicesReportRequest setDate(DateTime date) {
        this.date = Optional.of(date);
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

        if (date.isPresent()) {
            builder.addParameter("date", this.date.get().toString(DateFormats.DATE_FORMATTER));
        }

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }

    @Override
    public ResponseParser<DevicesReport> getResponseParser() {
        return new ResponseParser<DevicesReport>() {
            @Override
            public DevicesReport parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, DevicesReport.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() { return false; }
}
