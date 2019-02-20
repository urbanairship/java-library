package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableList;
import com.google.common.base.Optional;

import java.util.Objects;

public class Report {
    private final Optional<String> next_page;
    private final Optional<ImmutableList<Response>> responses;

    private Report(Optional<String> next_page, Optional<ImmutableList<Response>> responses) {
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
    public Optional<ImmutableList<Response>> getResponses() {
        return responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(next_page, report.next_page) &&
                Objects.equals(responses, report.responses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next_page, responses);
    }

    @Override
    public String toString() {
        return "Report{" +
                "next_page=" + next_page +
                ", responses=" + responses +
                '}';
    }

    public static class Builder {
        private String next_page = null;
        private ImmutableList.Builder<Response> responses = ImmutableList.builder();

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
        public Builder addResponseObject(Response object) {
            this.responses.add(object);
            return this;
        }

        /**
         * Add all Response objects for a listing
         *
         * @param object Iterable of Response objects
         * @return Builder
         */
        public Builder addResponseObject(Iterable<? extends Response> object) {
            this.responses.addAll(object);
            return this;
        }

        /**
         * Build the Response object
         *
         * @return Response
         */
        public Report build() {
            return new Report(Optional.fromNullable(next_page), Optional.fromNullable(responses.build()));
        }
    }
}
