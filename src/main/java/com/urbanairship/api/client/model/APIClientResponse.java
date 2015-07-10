/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.urbanairship.api.feedback.model.APIApidsFeedbackResponse;
import com.urbanairship.api.feedback.model.APIDeviceTokensFeedbackResponse;
import com.urbanairship.api.reports.model.AppStats;
import org.apache.http.HttpResponse;

import java.util.List;

/**
 * APIClientResponse encapsulates relevant data about responses from the
 * Urban Airship API. This is the object returned for successful API requests.
 * It contains the raw HttpResponse object and an API response object with
 * the details of the Urban Airship response specifically.
 */
public class APIClientResponse<T> {

    private final T apiResponse;
    private final HttpResponse httpResponse;

    private APIClientResponse(T apiResponse, HttpResponse
            httpResponse) {
        this.apiResponse = apiResponse;
        this.httpResponse = httpResponse;
    }

    /**
     * Return a Builder for a List of AppStats
     *
     * @return Builder
     */
    public static Builder<List<AppStats>> newListAppStatsBuilder() {
        return new Builder<List<AppStats>>();
    }

    /**
     * Return a Builder for a List of APIApidsFeedbackResponse
     *
     * @return Builder
     */
    public static Builder<List<APIApidsFeedbackResponse>> newListApidsFeedbackBuilder() {
        return new Builder<List<APIApidsFeedbackResponse>>();
    }

    /**
     * Return a Builder for a List of APIDeviceTokensFeedbackResponse
     *
     * @return Builder
     */
    public static Builder<List<APIDeviceTokensFeedbackResponse>> newListDeviceTokensFeedbackBuilder() {
        return new Builder<List<APIDeviceTokensFeedbackResponse>>();
    }

    /**
     * Return a Builder for a String
     *
     * @return Builder
     */
    public static Builder<String> newStringResponseBuilder() {
        return new Builder<String>();
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
     * The APIResponse object for this request. Returns one of the API response
     * types.
     *
     * @return APIResponse Type for this request.
     */
    public T getApiResponse() {
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
        final APIClientResponse other = (APIClientResponse) obj;
        return Objects.equal(this.apiResponse, other.apiResponse) && Objects.equal(this.httpResponse, other.httpResponse);
    }

    public String toString() {
        return "\nHttp:" + httpResponse.toString() + "\nAPI:" + apiResponse.toString();
    }

    /**
     * Build a APIClientResponse of the given type.
     */
    public static class Builder<T> {

        private T apiResponse;
        private HttpResponse httpResponse;

        public Builder() {
        }

        /**
         * The API response specific to the Urban Airship API request.
         *
         * @param apiResponse API response object.
         * @return This Builder
         */
        public Builder<T> setApiResponse(T apiResponse) {
            this.apiResponse = apiResponse;
            return this;
        }

        /**
         * The raw HttpResponse response that was returned for this
         * request.
         *
         * @param httpResponse An Apache HttpResponse.
         * @return This Builder
         */
        public Builder<T> setHttpResponse(HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
            return this;
        }

        public APIClientResponse<T> build() {
            return new APIClientResponse<T>(apiResponse, httpResponse);
        }

    }
}
