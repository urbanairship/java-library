package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

import java.util.Objects;

public class ResponseReport {
    private final Optional<String> next_page;
    private final Optional<ImmutableList<ResponseReportResponse>> responses;
    private final Optional<Boolean> ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private ResponseReport(
        Optional<String> next_page, 
        Optional<ImmutableList<ResponseReportResponse>> responses, 
        Boolean ok,
        String error,
        ErrorDetails errorDetails) {
        this.next_page = next_page;
        this.responses = responses;
        this.ok = Optional.ofNullable(ok);
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
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

        /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public Optional<Boolean> getOk() {
        return ok;
    }

    /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReport that = (ResponseReport) o;
        return Objects.equals(next_page, that.next_page) &&
                Objects.equals(responses, that.responses) &&
                Objects.equals(ok, that.ok) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next_page, responses, ok, error, errorDetails);
    }

    @Override
    public String toString() {
        return "ResponseReport{" +
                "next_page=" + next_page +
                ", responses=" + responses +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    public static class Builder {
        private String next_page = null;
        private ImmutableList.Builder<ResponseReportResponse> responses = ImmutableList.builder();
        private boolean ok = true;
        private String error;
        private ErrorDetails errorDetails;

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
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }


        /**
         * Build the Response object
         *
         * @return Response
         */
        public ResponseReport build() {
            return new ResponseReport(Optional.ofNullable(next_page), Optional.ofNullable(responses.build()), ok, error, errorDetails);
        }
    }
}
