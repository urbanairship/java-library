/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Objects;

import static com.urbanairship.api.common.parse.DateFormats.DATE_PARSER;

public class ExperimentVariantReportResponse {
    private final boolean ok;
    private final String appKey;
    private final String experimentId; 
    private final String pushId;
    private final DateTime created;
    private final Integer variant; 
    private final String variantName;
    private final Integer sends;
    private final Integer directResponses;
    private final Integer influencedResponses;
    private final ImmutableMap<String, ExperimentVariantStats> experimentVariantStats;
    private final String error;
    private final Integer errorCode;

    public ExperimentVariantReportResponse(
        @JsonProperty("ok") boolean ok,
        @JsonProperty("app_key") String appKey,
        @JsonProperty("experiment_id") String experimentId,
        @JsonProperty("push_id") String pushId,
        @JsonProperty("created") String createdString,
        @JsonProperty("variant") Integer variant,
        @JsonProperty("variant_name") String variantName,
        @JsonProperty("sends") Integer sends,
        @JsonProperty("direct_responses") Integer directResponses,
        @JsonProperty("influenced_responses") Integer influencedResponses,
        @JsonProperty("platforms") ImmutableMap<String, ExperimentVariantStats> experimentVariantStats,
        @JsonProperty("error") String error,
        @JsonProperty("error_code") Integer errorCode
    ) {
        this.ok = ok;
        this.appKey = appKey;
        this.experimentId = experimentId;
        this.pushId = pushId;
        this.created = DATE_PARSER.parseDateTime(createdString);
        this.variant = variant;
        this.variantName = variantName;
        this.sends = sends;
        this.directResponses = directResponses;
        this.influencedResponses = influencedResponses;
        this.experimentVariantStats = experimentVariantStats;
        this.error = error;
        this.errorCode = errorCode;
    }

    public boolean getOk() {
        return ok;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public String getPushId() {
        return pushId;
    }

    public DateTime getCreated() {
        return created;
    }

    public Integer getVariant() {
        return variant;
    }

    public String getVariantName() {
        return variantName;
    }

    public Integer getSends() {
        return sends;
    }

    public Integer getDirectResponses() {
        return directResponses;
    }

    public Integer getInfluencedResponses() {
        return influencedResponses;
    }

    public ImmutableMap<String, ExperimentVariantStats> getVariantPlatforms() {
        return experimentVariantStats;
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
        ExperimentVariantReportResponse that = (ExperimentVariantReportResponse) o;
        return ok == that.ok &&
                Objects.equals(appKey, that.appKey) &&
                Objects.equals(experimentId, that.experimentId) &&
                Objects.equals(pushId, that.pushId) &&
                Objects.equals(created, that.created) &&
                Objects.equals(variant, that.variant) &&
                Objects.equals(variantName, that.variantName) &&
                Objects.equals(sends, that.sends) &&
                Objects.equals(directResponses, that.directResponses) &&
                Objects.equals(influencedResponses, that.influencedResponses) &&
                Objects.equals(experimentVariantStats, that.experimentVariantStats) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ok, appKey, experimentId, pushId, created, variant, variantName, sends, directResponses, influencedResponses, experimentVariantStats, error, errorCode);
    }

    @Override
    public String toString() {
        return "ExperimentVariantReportResponse{" +
                "appKey=" + appKey +
                ", experimentId=" + experimentId +
                ", pushId=" + pushId +
                ", created=" + created +
                ", variant=" + variant +
                ", variantName=" + variantName +
                ", sends=" + sends +
                ", directResponses=" + directResponses +
                ", influencedResponses=" + influencedResponses +
                ", experimentVariantStats=" + experimentVariantStats +
                ", error=" + error +
                ", errorCode=" + errorCode +
                '}';
    }
}
