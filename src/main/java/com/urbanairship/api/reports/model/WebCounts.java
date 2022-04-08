package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.Objects;

import static com.urbanairship.api.common.parse.DateFormats.DATE_PARSER;

public class WebCounts {

    private final WebCountsStats counts;
    private final DateTime date;

    public WebCounts(
            @JsonProperty("counts") WebCountsStats counts,
            @JsonProperty("date") String dateString) {
        this.counts = counts;
        this.date = DATE_PARSER.parseDateTime(dateString);
    }

    public WebCountsStats getCounts() {
        return counts;
    }

    public DateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebCounts webCounts = (WebCounts) o;
        return Objects.equals(counts, webCounts.counts) &&
                Objects.equals(date, webCounts.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(counts, date);
    }

    @Override
    public String toString() {
        return "WebCounts{" +
                "counts=" + counts +
                ", date=" + date +
                '}';
    }
}
