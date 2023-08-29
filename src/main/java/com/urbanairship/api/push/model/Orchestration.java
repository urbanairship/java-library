package com.urbanairship.api.push.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Orchestration {
    private final List<String> orchestrationChannelPriority;
    private final OrchestrationType orchestrationType;

    public Orchestration(List<String> orchestrationChannelPriority, OrchestrationType orchestrationType) {
        this.orchestrationChannelPriority = orchestrationChannelPriority;
        this.orchestrationType = orchestrationType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<String> getOrchestrationChannelPriority() {
        return orchestrationChannelPriority;
    }

    public OrchestrationType getOrchestrationType() {
        return orchestrationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orchestration that = (Orchestration) o;
        return Objects.equals(orchestrationChannelPriority, that.orchestrationChannelPriority) &&
                orchestrationType == that.orchestrationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orchestrationChannelPriority, orchestrationType);
    }

    @Override
    public String toString() {
        return "Orchestration{" +
                "orchestrationChannelPriority=" + orchestrationChannelPriority +
                ", orchestrationType=" + orchestrationType +
                '}';
    }

    public static class Builder {
        private final ImmutableList.Builder<String> orchestrationChannelPriorityBuilder = ImmutableList.builder();
        private OrchestrationType orchestrationType = null;

        public Builder addOrchestrationChannelPriority(String orchestrationChannelPriority) {
            this.orchestrationChannelPriorityBuilder.add(orchestrationChannelPriority);
            return this;
        }

        public Builder addAllOrchestrationChannelPriority(List<String> OrchestrationChannelPriority) {
            this.orchestrationChannelPriorityBuilder.addAll(OrchestrationChannelPriority);
            return this;
        }

        public Builder setOrchestrationType(OrchestrationType orchestrationType) {
            this.orchestrationType = orchestrationType;
            return this;
        }

        public Orchestration build() {
            List<String> orchestrationChannelPriority = orchestrationChannelPriorityBuilder.build();
            Preconditions.checkArgument(!(orchestrationType
                            .equals(OrchestrationType.CHANNEL_PRIORITY) && orchestrationChannelPriority.isEmpty()),
                    "Channel Priority is required when type is set to channel_priority");

            return new Orchestration(
                    orchestrationChannelPriority,
                    orchestrationType
            );
        }
    }

}
