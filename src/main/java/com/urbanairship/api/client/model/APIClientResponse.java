
/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.segments.model.AudienceSegment;
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
            httpResponse){
        this.apiResponse = apiResponse;
        this.httpResponse = httpResponse;
    }

    /**
     * Return a Builder for an APIPushResponse
     * @return Builder
     */
    public static Builder<APIPushResponse> newPushResponseBuilder(){
        return new Builder<APIPushResponse>();
    }

    /**
     * Return a Builder for an APIScheduleResponse
     * @return Builder
     */
    public static Builder<APIScheduleResponse> newScheduleResponseBuilder(){
        return new Builder<APIScheduleResponse>();
    }

    /**
     * Return a Builder for an APIListAllSchedulesResponse
     * @return Builder
     */
    public static Builder<APIListAllSchedulesResponse> newListAllSchedulesResponseBuilder(){
        return new Builder<APIListAllSchedulesResponse>();
    }

    /**
     * Return a Builder for an SchedulePayload
     * @return Builder
     */
    public static Builder<SchedulePayload> newSchedulePayloadBuilder(){
        return new Builder<SchedulePayload>();
    }

    /**
     * Return a Builder for an APIListTagsResponse
     * @return Builder
     */
    public static Builder<APIListTagsResponse> newListTagsResponseBuilder(){
        return new Builder<APIListTagsResponse>();
    }

    /**
     * Return a Builder for a SinglePushInfoResponse
     * @return Builder
     */
    public static Builder<SinglePushInfoResponse> newSinglePushInfoResponseBuilder(){
        return new Builder<SinglePushInfoResponse>();
    }

    /**
     * Return a Builder for a APIReportsListingResponse
     * @return Builder
     */
    public static Builder<APIReportsPushListingResponse> newReportsListingResponseBuilder(){
        return new Builder<APIReportsPushListingResponse>();
    }

    /**
     * Return a Builder for a List of AppStats
     * @return Builder
     */
    public static Builder<List<AppStats>> newListAppStatsBuilder(){
        return new Builder<List<AppStats>>();
    }

    /**
     * Return a Builder for a ReportsAPIOpensResponse
     * @return Builder
     */
    public static Builder<ReportsAPIOpensResponse> newAppsOpenReportResponseBuilder(){
        return new Builder<ReportsAPIOpensResponse>();
    }

    /**
     * Return a Builder for a ReportsAPITimeInAppResponse
     * @return Builder
     */
    public static Builder<ReportsAPITimeInAppResponse> newTimeInAppReportResponseBuilder(){
        return new Builder<ReportsAPITimeInAppResponse>();
    }

    /**
     * Return a Builder for a String
     * @return Builder
     */
    public static Builder<String> newStringResponseBuilder(){
        return new Builder<String>();
    }

    /**
     * Return a Builder for an APIListSingleChannelResponse
     * @return Builder
     */
    public static Builder<APIListSingleChannelResponse> newSingleChannelResponseBuilder(){
        return new Builder<APIListSingleChannelResponse>();
    }

    /**
     * Return a Builder for an APIListAllChannelsResponse
     * @return Builder
     */
    public static Builder<APIListAllChannelsResponse> newListAllChannelsResponseBuilder(){
        return new Builder<APIListAllChannelsResponse>();
    }

    /**
     * Return a Builder for an APIListAllSegmentsResponse
     * @return Builder
     */
    public static Builder<APIListAllSegmentsResponse> newListAllSegmentsResponseBuilder(){
        return new Builder<APIListAllSegmentsResponse>();
    }

    /**
     * Return a Builder for an AudienceSegment
     * @return Builder
     */
    public static Builder<AudienceSegment> newAudienceSegmentResponseBuilder(){
        return new Builder<AudienceSegment>();
    }

    /**
     * Return a Builder for an APILocationResponse
     * @return Builder
     */
    public static Builder<APILocationResponse> newLocationResponseBuilder(){
        return new Builder<APILocationResponse>();
    }

    /**
     * Return the HTTP request object used for the request.
     * The HttpEntity associated with the request will be closed, and
     * attempting to read from it will throw an exception
     * @return HttpResponse returned with this request
     */
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * The APIResponse object for this request. Returns one of the API response
     * types.
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

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("\nHttp:");
        builder.append(httpResponse.toString());
        builder.append("\nAPI:");
        builder.append(apiResponse.toString());
        return builder.toString();
    }

    /**
     * Build a APIClientResponse of the given type.
     */
    public static class Builder<T> {

        private T apiResponse;
        private HttpResponse httpResponse;

        private Builder(){}

        /**
         * The API response specific to the Urban Airship API request.
         * @param apiResponse API response object.
         * @return This Builder
         */
        public Builder<T> setApiResponse(T apiResponse){
            this.apiResponse = apiResponse;
            return this;
        }

        /**
         * The raw HttpResponse response that was returned for this
         * request.
         * @param httpResponse An Apache HttpResponse.
         * @return This Builder
         */
        public Builder<T> setHttpResponse(HttpResponse httpResponse){
            this.httpResponse = httpResponse;
            return this;
        }

        public APIClientResponse<T> build(){
            return new APIClientResponse<T>(apiResponse, httpResponse);
        }

    }
}
