package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WebCountsStats {

    private final Integer clicks;
    private final Integer sessions;

    public WebCountsStats(
            @JsonProperty("clicks") Integer clicks,
            @JsonProperty("sessions") Integer sessions) {
        this.clicks = clicks;
        this.sessions = sessions;
    }

    public Integer getClicks() {
        return clicks;
    }

    public Integer getSessions() {
        return sessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebCountsStats that = (WebCountsStats) o;
        return Objects.equals(clicks, that.clicks) &&
                Objects.equals(sessions, that.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clicks, sessions);
    }

    @Override
    public String toString() {
        return "WebCountsStats{" +
                "clicks=" + clicks +
                ", sessions=" + sessions +
                '}';
    }
}
