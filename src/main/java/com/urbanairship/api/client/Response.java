/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.urbanairship.api.reports.model.AppStats;
import org.apache.http.HttpResponse;

import java.util.List;

/**
 * Response encapsulates relevant data about responses from the
 * Urban Airship API. This is the object returned for successful API requests.
 * It contains the raw HttpResponse object and an optional parsed API response object with
 * the details of the Urban Airship response specifically.
 */
public class Response<T> {

    private final Optional<T> apiResponse;
    private final HttpResponse httpResponse;

    /**
     * Default constructor.
     *
     * @param apiResponse Optional parsed api response.
     * @param httpResponse HttpResponse object.
     */
    Response(T apiResponse, HttpResponse httpResponse) {
        this.apiResponse = Optional.fromNullable(apiResponse);
        this.httpResponse = httpResponse;
    }

    /**
     * Return the HTTP request object used for the request.
     * The HttpEntity associated with the request will be closed, and
     * attempting to read from it will throw an exception
     *
     * @return HttpResponse returned with this request
     */
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * Gets the parsed request response.
     *
     * @return Parsed request response.
     */
    public Optional<T> getApiResponse() {
        return apiResponse;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(apiResponse, httpResponse);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Response other = (Response) obj;
        return Objects.equal(this.apiResponse, other.apiResponse) && Objects.equal(this.httpResponse, other.httpResponse);
    }

    @Override
    public String toString() {
        return "\nHttp:" + httpResponse.toString() + "\nAPI:" + apiResponse.toString();
    }
}
