/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import org.apache.http.entity.ContentType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * The base interface for UrbanAirshipClient API requests
 */

public interface Request<T> {

    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String UA_VERSION = "application/vnd.urbanairship+json; version=3";

    public static enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE,
    }

    @Nonnull
    HttpMethod getHttpMethod();

    @Nullable
    String getRequestBody();

    @Nullable
    ContentType getContentType();

    @Nullable
    Map<String, String> getRequestHeaders();

    @Nonnull
    URI getUri(URI baseUri) throws URISyntaxException;

    @Nonnull
    ResponseParser<T> getResponseParser();
}