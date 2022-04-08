/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ExperimentVariant {

    private final Integer id;
    private final String name;
    private final Float audiencePct;
    private final Integer sends;
    private final Integer directResponses;
    private final Float directResponsesPct;
    private final Integer indirectResponses;
    private final Float indirectResponsesPct;

    public ExperimentVariant(
            @JsonProperty("id") Integer id,
            @JsonProperty("name") String name,
            @JsonProperty("audience_pct") Float audiencePct,
            @JsonProperty("sends") Integer sends,
            @JsonProperty("direct_responses") Integer directResponses,
            @JsonProperty("direct_response_pct") Float directResponsesPct,
            @JsonProperty("indirect_responses") Integer indirectResponses,
            @JsonProperty("indirect_response_pct") Float indirectResponsesPct) {
        this.id = id;
        this.name = name;
        this.audiencePct = audiencePct;
        this.sends = sends;
        this.directResponses = directResponses;
        this.directResponsesPct = directResponsesPct;
        this.indirectResponses = indirectResponses;
        this.indirectResponsesPct = indirectResponsesPct;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getAudiencePct() {
        return audiencePct;
    }

    public Integer getSends() {
        return sends;
    }

    public Integer getDirectResponses() {
        return directResponses;
    }

    public Float getDirectResponsesPct() {
        return directResponsesPct;
    }

    public Integer getIndirectResponses() {
        return indirectResponses;
    }

    public Float getIndirectResponsesPct() {
        return indirectResponsesPct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentVariant that = (ExperimentVariant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(audiencePct, that.audiencePct) &&
                Objects.equals(sends, that.sends) &&
                Objects.equals(directResponses, that.directResponses) &&
                Objects.equals(directResponsesPct, that.directResponsesPct) &&
                Objects.equals(indirectResponses, that.indirectResponses) &&
                Objects.equals(indirectResponsesPct, that.indirectResponsesPct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, audiencePct, sends, directResponses, directResponsesPct, indirectResponses, indirectResponsesPct);
    }

    @Override
    public String toString() {
        return "ExperimentVariant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", audiencePct=" + audiencePct +
                ", sends=" + sends +
                ", directResponses=" + directResponses +
                ", directResponsesPct=" + directResponsesPct +
                ", indirectResponses=" + indirectResponses +
                ", indirectResponsesPct=" + indirectResponsesPct +
                '}';
    }
}
