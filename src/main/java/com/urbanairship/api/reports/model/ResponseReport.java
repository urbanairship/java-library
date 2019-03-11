package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableList;
import java.util.Optional;

import java.util.Objects;

public class ResponseReport {
    private final Optional<String> next_page;
    private final Optional<ImmutableList<ResponseReportResponse>> responses;

    private ResponseReport(Optional<String> next_page, Optional<ImmutableList<ResponseReportResponse>> responses) {
        this.next_page = next_page;
        this.responses = responses;
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * Get the next page attribute if present for a ReportResponseRequest.
     *
     * @return An optional string
     */
    public Optional<String> getNextPage() {
        return next_page;
    }

    /**
     * Get the list of Response objects for a ReportResponseRequest
     *
     * @return An optional immutable list of Response objects
     */
    public Optional<ImmutableList<ResponseReportResponse>> getResponses() {
        return responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReport that = (ResponseReport) o;
        return Objects.equals(next_page, that.next_page) &&
                Objects.equals(responses, that.responses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next_page, responses);
    }

    @Override
    public String toString() {
        return "ResponseReport{" +
                "next_page=" + next_page +
                ", responses=" + responses +
                '}';
    }

    public static class Builder {
        private String next_page = null;
        private ImmutableList.Builder<ResponseReportResponse> responses = ImmutableList.builder();

        private Builder() {}

        /**
         * Set the next page
         *
         * @param next_page String
         * @return Builder
         */
        public Builder setNextPage(String next_page) {
            this.next_page = next_page;
            return this;
        }

        /**
         * Add a Response object for a listing
         *
         * @param object Response
         * @return Builder
         */
        public Builder addResponseObject(ResponseReportResponse object) {
            this.responses.add(object);
            return this;
        }

        /**
         * Add all Response objects for a listing
         *
         * @param object Iterable of Response objects
         * @return Builder
         */
        public Builder addResponseObject(Iterable<? extends ResponseReportResponse> object) {
            this.responses.addAll(object);
            return this;
        }

        /**
         * Build the Response object
         *
         * @return Response
         */
        public ResponseReport build() {
            return new ResponseReport(Optional.ofNullable(next_page), Optional.ofNullable(responses.build()));
        }
    }
}
