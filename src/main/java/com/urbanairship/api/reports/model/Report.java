package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableList;
import com.google.common.base.Optional;

import java.util.Objects;

//TODO: make sure to add commnets to the needed functions, look at other for example

public class Report {
    private final Optional<String> next_page;
    private final Optional<ImmutableList<Response>> responses;

    private Report(Optional<String> next_page, Optional<ImmutableList<Response>> responses) {
        this.next_page = next_page;
        this.responses = responses;
    }

    public static Builder newBuilder() { return new Builder(); }

    public Optional<String> getNext_page() {
        return next_page;
    }

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

        public Builder setNextPage(String next_page) {
            this.next_page = next_page;
            return this;
        }

        public Builder addResponseObject(Response item) {
            this.responses.add(item);
            return this;
        }

        public Builder addResponseObject(Iterable<? extends Response> item) {
            this.responses.addAll(item);
            return this;
        }

        public Report build() {
            return new Report(Optional.fromNullable(next_page), Optional.fromNullable(responses.build()));
        }
    }
}
