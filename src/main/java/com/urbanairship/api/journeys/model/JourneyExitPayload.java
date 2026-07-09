package com.urbanairship.api.journeys.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Payload for POST /api/journeys/exit — exits in-flight users from a Sequence.
 * This endpoint only supports OAuth authentication.
 */
public final class JourneyExitPayload extends PushModelObject {

    private final List<String> triggeringIds;
    private final Optional<String> entranceId;

    private JourneyExitPayload(Builder builder) {
        this.triggeringIds = Collections.unmodifiableList(new ArrayList<>(builder.triggeringIds));
        this.entranceId = Optional.ofNullable(builder.entranceId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The triggering ID(s) of the Sequence's configured API Entrance trigger.
     * When only one ID was set, singletons are serialized as a plain string;
     * multiple IDs are serialized as a JSON array.
     *
     * @return List of triggering IDs (1–10 elements)
     */
    public List<String> getTriggeringIds() {
        return triggeringIds;
    }

    /**
     * If provided, only users that entered with this entrance ID are exited.
     * If omitted, only users that entered without an entrance ID are exited.
     * Min length 1, max length 64.
     *
     * @return Optional entrance ID
     */
    public Optional<String> getEntranceId() {
        return entranceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourneyExitPayload that = (JourneyExitPayload) o;
        return Objects.equal(triggeringIds, that.triggeringIds)
                && Objects.equal(entranceId, that.entranceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(triggeringIds, entranceId);
    }

    @Override
    public String toString() {
        return "JourneyExitPayload{"
                + "triggeringIds=" + triggeringIds
                + ", entranceId=" + entranceId
                + '}';
    }

    public static final class Builder {
        private final List<String> triggeringIds = new ArrayList<>();
        private String entranceId = null;

        private Builder() {}

        /**
         * Set a single triggering ID. Required when not using {@link #addTriggeringId(String)}.
         *
         * @param triggeringId Sequence API Entrance trigger identifier
         * @return Builder
         */
        public Builder setTriggeringId(String triggeringId) {
            this.triggeringIds.clear();
            this.triggeringIds.add(triggeringId);
            return this;
        }

        /**
         * Add a triggering ID. Up to 10 IDs may be provided.
         *
         * @param triggeringId Sequence API Entrance trigger identifier
         * @return Builder
         */
        public Builder addTriggeringId(String triggeringId) {
            this.triggeringIds.add(triggeringId);
            return this;
        }

        /**
         * Set the entrance ID to target a specific enrollment for exit.
         * If omitted, only users that entered without an entrance ID are exited.
         * Min length 1, max length 64.
         *
         * @param entranceId Entrance identifier
         * @return Builder
         */
        public Builder setEntranceId(String entranceId) {
            this.entranceId = entranceId;
            return this;
        }

        public JourneyExitPayload build() {
            Preconditions.checkArgument(!triggeringIds.isEmpty(), "At least one triggering ID is required");
            Preconditions.checkArgument(triggeringIds.size() <= 10, "A maximum of 10 triggering IDs are allowed");
            if (entranceId != null) {
                Preconditions.checkArgument(!entranceId.isEmpty(), "'entranceId' must not be empty");
                Preconditions.checkArgument(entranceId.length() <= 64, "'entranceId' must not exceed 64 characters");
            }
            return new JourneyExitPayload(this);
        }
    }
}
