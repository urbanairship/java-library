/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;

import java.util.Objects;

import static com.urbanairship.api.common.parse.DateFormats.DATE_PARSER;

public class ExperimentOverviewReportResponse {
    private final boolean ok;
    private final String appKey;
    private final String experimentId;
    private final String pushId;
    private final DateTime created;
    private final Integer sends;
    private final Integer directResponses;
    private final Integer influencedResponses;
    private final Integer webClicks;
    private final Integer webSessions;
    private final ImmutableList<ExperimentVariant> experimentVariants;
    private final ExperimentControl experimentControl;
    private final String error;
    private final Integer errorCode;

    public ExperimentOverviewReportResponse(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("app_key") String appKey,
            @JsonProperty("experiment_id") String experimentId,
            @JsonProperty("push_id") String pushId,
            @JsonProperty("created") String createdString,
            @JsonProperty("sends") Integer sends,
            @JsonProperty("direct_responses") Integer directResponses,
            @JsonProperty("influenced_responses") Integer influencedResponses,
            @JsonProperty("web_clicks") Integer webClicks,
            @JsonProperty("web_sessions") Integer webSessions,
            @JsonProperty("variants") ImmutableList<ExperimentVariant> experimentVariants,
            @JsonProperty("control") ExperimentControl experimentControl,
            @JsonProperty("error") String error,
            @JsonProperty("error_code") Integer errorCode
    ) {
        this.ok = ok;
        this.appKey = appKey;
        this.experimentId = experimentId;
        this.pushId = pushId;
        this.created = DATE_PARSER.parseDateTime(createdString);
        this.sends = sends;
        this.directResponses = directResponses;
        this.influencedResponses = influencedResponses;
        this.webClicks = webClicks;
        this.webSessions = webSessions;
        this.experimentVariants = experimentVariants;
        this.experimentControl = experimentControl;
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

    public Integer getSends() {
        return sends;
    }

    public Integer getDirectResponses() {
        return directResponses;
    }

    public Integer getInfluencedResponses() {
        return influencedResponses;
    }

    public Integer getWebClicks() {
        return webClicks;
    }

    public Integer getWebSessions() {
        return webSessions;
    }

    public ImmutableList<ExperimentVariant> getExperimentVariants() {
        return experimentVariants;
    }

    public ExperimentControl getExperimentControl() {
        return experimentControl;
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
        ExperimentOverviewReportResponse that = (ExperimentOverviewReportResponse) o;
        return ok == that.ok &&
                Objects.equals(appKey, that.appKey) &&
                Objects.equals(experimentId, that.experimentId) &&
                Objects.equals(pushId, that.pushId) &&
                Objects.equals(created, that.created) &&
                Objects.equals(sends, that.sends) &&
                Objects.equals(directResponses, that.directResponses) &&
                Objects.equals(influencedResponses, that.influencedResponses) &&
                Objects.equals(webClicks, that.webClicks) &&
                Objects.equals(webSessions, that.webSessions) &&
                Objects.equals(experimentVariants, that.experimentVariants) &&
                Objects.equals(experimentControl, that.experimentControl) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ok, appKey, experimentId, pushId, created, sends, directResponses, influencedResponses, webClicks, webSessions, experimentVariants, experimentControl, error, errorCode);
    }

    @Override
    public String toString() {
        return "ExperimentOverviewReportResponse{" +
                "ok=" + ok +
                ", appKey='" + appKey + '\'' +
                ", experimentId='" + experimentId + '\'' +
                ", pushId='" + pushId + '\'' +
                ", created='" + created + '\'' +
                ", sends=" + sends +
                ", directResponses=" + directResponses +
                ", influencedResponses=" + influencedResponses +
                ", webClicks=" + webClicks +
                ", webSessions=" + webSessions +
                ", experimentVariants=" + experimentVariants +
                ", experimentControl=" + experimentControl +
                ", error='" + error + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
