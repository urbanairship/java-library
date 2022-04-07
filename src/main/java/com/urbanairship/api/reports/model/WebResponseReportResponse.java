/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;

import java.util.Objects;

import static com.urbanairship.api.common.parse.DateFormats.DATE_PARSER;

public class WebResponseReportResponse {
    private final boolean ok;
    private final String appKey;
    private final DateTime end;
    private final Precision precision;
    private final DateTime start;
    private final ImmutableList<WebCounts> webTotalCounts;
    private final String error;
    private final Integer errorCode;

    public WebResponseReportResponse(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("app_key") String appKey,
            @JsonProperty("end") String endString,
            @JsonProperty("precision") Precision precision,
            @JsonProperty("start") String startString,
            @JsonProperty("total_counts") ImmutableList<WebCounts> webTotalCounts,
            @JsonProperty("error") String error,
            @JsonProperty("error_code") Integer errorCode
    ) {
        this.ok = ok;
        this.appKey = appKey;
        this.end = DATE_PARSER.parseDateTime(endString);
        this.precision = precision;
        this.start = DATE_PARSER.parseDateTime(startString);
        this.webTotalCounts = webTotalCounts;
        this.error = error;
        this.errorCode = errorCode;
    }

    public boolean isOk() {
        return ok;
    }

    public String getAppKey() {
        return appKey;
    }

    public DateTime getEnd() {
        return end;
    }

    public Precision getPrecision() {
        return precision;
    }

    public DateTime getStart() {
        return start;
    }

    public ImmutableList<WebCounts> getWebTotalCounts() {
        return webTotalCounts;
    }

    public String getError() {
        return error;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebResponseReportResponse that = (WebResponseReportResponse) o;
        return ok == that.ok &&
                Objects.equals(appKey, that.appKey) &&
                Objects.equals(end, that.end) &&
                precision == that.precision &&
                Objects.equals(start, that.start) &&
                Objects.equals(webTotalCounts, that.webTotalCounts) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ok, appKey, end, precision, start, webTotalCounts, error, errorCode);
    }

    @Override
    public String toString() {
        return "WebResponseReportResponse{" +
                "ok=" + ok +
                ", appKey='" + appKey + '\'' +
                ", end='" + end + '\'' +
                ", precision=" + precision +
                ", start='" + start + '\'' +
                ", webTotalCounts=" + webTotalCounts +
                ", error='" + error + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
