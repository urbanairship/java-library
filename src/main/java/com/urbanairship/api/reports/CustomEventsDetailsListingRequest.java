package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.CustomEventsDetailsListingResponse;
import com.urbanairship.api.reports.model.Precision;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The CustomEventsDetailsListingRequest class builds a request to the custom events details listing api
 * endpoint to be executed in the {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class CustomEventsDetailsListingRequest implements Request<CustomEventsDetailsListingResponse> {
    private final static String API_RESPONSE_REPORT = "/api/reports/events/";
    private final String path;
    private final boolean nextPageRequest;
    private DateTime start;
    private DateTime end;
    private Precision precision;
    private Integer page;
    private Integer pageSize;

    private final static ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.registerModule(new GuavaModule());
    }

    private CustomEventsDetailsListingRequest(DateTime start, DateTime end, String path, boolean nextPageRequest) {
        this.path = path;
        this.nextPageRequest = nextPageRequest;
        this.start = start;
        this.end = end;
    }

    /**
     * Create a Response Report request.
     *
     * @param start DateTime
     * @param end DateTime
     * @return CustomEventsDetailsListingRequest
     */
    public static CustomEventsDetailsListingRequest newRequest(DateTime start, DateTime end) {
        return new CustomEventsDetailsListingRequest(start, end, API_RESPONSE_REPORT, false);
    }

    /**
     * Create a new Response Report request using a next page URI.
     *
     * @param start DateTime
     * @param end DateTime
     * @param nextPage URI
     * @return CustomEventsDetailsListingRequest
     */
    public static CustomEventsDetailsListingRequest newRequest(DateTime start, DateTime end, URI nextPage) {
        Preconditions.checkNotNull(nextPage, "Next page URI cannot be null");
        return new CustomEventsDetailsListingRequest(start, end, nextPage.getPath() + "?" + nextPage.getQuery(), true);
    }

    /**
     * Set the precision parameter.
     *
     * @param precision Precision
     * @return CustomEventsDetailsListingRequest
     */
    public CustomEventsDetailsListingRequest setPrecision(Precision precision) {
        this.precision = precision;
        return this;
    }

    /**
     * Set the page parameter.
     *
     * @param page An int
     * @return CustomEventsDetailsListingRequest
     */
    public CustomEventsDetailsListingRequest setPage(Integer page) {
        this.page = page;
        return this;
    }

    /**
     * Set the page_size parameter.
     *
     * @param pageSize An int
     * @return CustomEventsDetailsListingRequest
     */
    public CustomEventsDetailsListingRequest setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
            Preconditions.checkArgument(end.isAfter(start), "end date must occur after start date");

            builder.addParameter("start", this.start.toString(DateFormats.DATE_FORMATTER));
            builder.addParameter("end", this.end.toString(DateFormats.DATE_FORMATTER));
            if (this.precision != null) {
                builder.addParameter("precision", this.precision.toString());
            }
            if (this.page != null) {
                builder.addParameter("page", this.page.toString());
            }
            if (this.pageSize != null) {
                builder.addParameter("page_size", this.pageSize.toString());
            }
        }

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }

    @Override
    public ResponseParser<CustomEventsDetailsListingResponse> getResponseParser() {
        return response -> mapper.readValue(response, CustomEventsDetailsListingResponse.class);
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
