package com.urbanairship.api.push.model;

import java.util.Optional;

/**
 * Enum for specifying the orchestration type.
 */
public enum OrchestrationType {
    CHANNEL_PRIORITY("channel_priority"),
    TRIGGERING_CHANNEL("triggering_channel"),
    LAST_ACTIVE("last_active"),
    FAN_OUT("fan_out");

    private final String orchestrationType;

    OrchestrationType(String orchestrationType) {
        this.orchestrationType = orchestrationType;
    }

    public static Optional<OrchestrationType> find(String orchestrationType) {
        for (OrchestrationType type : values()) {
            if (type.orchestrationType.equals(orchestrationType)) {
                return Optional.of(type);
            }
        }

        return Optional.empty();
    }

    public String getOrchestrationType() {
        return orchestrationType;
    }
}
