/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class ExperimentVariantStats {

    private final String type;
    private final Integer directResponses;
    private final Integer indirectResponses;
    private final Integer influencedResponses;
    private final Integer sends;

    public ExperimentVariantStats(
            @JsonProperty("type") String type,
            @JsonProperty("direct_responses") Integer directResponses,
            @JsonProperty("indirect_responses") Integer indirectResponses,
            @JsonProperty("influenced_responses") Integer influencedResponses,
            @JsonProperty("sends") Integer sends
    ) {
        this.type = type;
        this.directResponses = directResponses;
        this.indirectResponses = indirectResponses;
        this.influencedResponses = influencedResponses;
        this.sends = sends;
    }
     public String getType() {
        return type;
     }

    public Integer getDirectResponses() {
        return directResponses;
    }

    public Integer getIndirectResponses() {
        return indirectResponses;
    }

    public Integer getInfluencedResponses() {
        return influencedResponses;
    }

    public Integer getSends() {
        return sends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentVariantStats that = (ExperimentVariantStats) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(directResponses, that.directResponses) &&
                Objects.equals(indirectResponses, that.indirectResponses) &&
                Objects.equals(influencedResponses, that.influencedResponses) &&
                Objects.equals(sends, that.sends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, directResponses, indirectResponses, influencedResponses, sends);
    }

    @Override
    public String toString() {
        return "ExperimentVariantStats{" +
                "type='" + type +
                ", directResponses=" + directResponses +
                ", indirectResponses=" + indirectResponses +
                ", influencedResponses=" + influencedResponses +
                ", sends=" + sends +
                '}';
    }
}
