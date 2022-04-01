/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */


package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ExperimentControl {
    private final Float audiencePct;
    private final Integer sends;
    private final Integer responses;
    private final Float responsesRatePct;

    public ExperimentControl(
            @JsonProperty("audience_pct") Float audiencePct,
            @JsonProperty("sends") Integer sends,
            @JsonProperty("responses") Integer responses,
            @JsonProperty("response_rate_pct") Float responsesRatePct) {
        this.audiencePct = audiencePct;
        this.sends = sends;
        this.responses = responses;
        this.responsesRatePct = responsesRatePct;
    }

    public Float getAudiencePct() {
        return audiencePct;
    }

    public Integer getSends() {
        return sends;
    }

    public Integer getResponses() {
        return responses;
    }

    public Float getResponsesRatePct() {
        return responsesRatePct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentControl that = (ExperimentControl) o;
        return Objects.equals(audiencePct, that.audiencePct) &&
                Objects.equals(sends, that.sends) &&
                Objects.equals(responses, that.responses) &&
                Objects.equals(responsesRatePct, that.responsesRatePct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(audiencePct, sends, responses, responsesRatePct);
    }

    @Override
    public String toString() {
        return "ExperimentControl{" +
                "audiencePct=" + audiencePct +
                ", sends=" + sends +
                ", responses=" + responses +
                ", responsesRatePct=" + responsesRatePct +
                '}';
    }
}
