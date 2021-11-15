/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.LocationResponse;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.location.parse.LocationObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The LocationRequest class builds location lookup requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class LocationRequest implements Request<LocationResponse> {

    private final static String API_LOCATION_PATH = "/api/location/";

    private final String path;
    private final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();

    private LocationRequest(String path, BasicNameValuePair parameter) {
        this.path = path;

        if (parameter != null) {
            this.parameters.add(parameter);
        }
    }

    /**
     * Create a location request with a query.
     *
     * @param query The query as a string.
     * @return LocationRequest
     */
    public static LocationRequest newRequest(String query) {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");
        return new LocationRequest(API_LOCATION_PATH, new BasicNameValuePair("q", query));
    }

    /**
     * Create a location request with a latitude and longitude.
     *
     * @param point The latitude and longitude encapsulated in a Point object.
     * @return LocationRequest
     */
    public static LocationRequest newRequest(Point point) {
        Preconditions.checkNotNull(point, "Point must not be null");
        return new LocationRequest(API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude(), null);
    }

    /**
     * Create a location request with a BoundedBox.
     *
     * @param boundedBox The bounded box.
     * @return LocationRequest
     */
    public static LocationRequest newRequest(BoundedBox boundedBox) {
        Preconditions.checkNotNull(boundedBox, "Box must not be null");
        return new LocationRequest(API_LOCATION_PATH +
            boundedBox.getCornerOne().getLatitude() + "," +
            boundedBox.getCornerOne().getLongitude() + "," +
            boundedBox.getCornerTwo().getLatitude() + "," +
            boundedBox.getCornerTwo().getLongitude()
        , null);
    }

    /**
     * Set the boundary type.
     *
     * @param type The type as a string.
     * @return LocationRequest
     */
    public LocationRequest setType(String type) {
        parameters.add(new BasicNameValuePair("type", type));
        return this;
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
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));
        for (BasicNameValuePair parameter : parameters) {
            builder.addParameter(parameter.getName(), parameter.getValue());
        }

        return builder.build();
    }

    @Override
    public ResponseParser<LocationResponse> getResponseParser() {
        return new ResponseParser<LocationResponse>() {
            @Override
            public LocationResponse parse(String response) throws IOException {
                return LocationObjectMapper.getInstance().readValue(response, LocationResponse.class);
            }
        };
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
