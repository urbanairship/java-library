package com.urbanairship.api.journeys.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.audience.Selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Payload for POST /api/journeys/trigger — enters an audience into a Sequence.
 * This endpoint only supports OAuth authentication.
 */
public final class JourneyTriggerPayload extends PushModelObject {

    private final Selector audience;
    private final List<String> triggeringIds;
    private final Optional<String> entranceId;
    private final Optional<Map<String, Object>> globalAttributes;

    private JourneyTriggerPayload(Builder builder) {
        this.audience = builder.audience;
        this.triggeringIds = Collections.unmodifiableList(new ArrayList<>(builder.triggeringIds));
        this.entranceId = Optional.ofNullable(builder.entranceId);
        this.globalAttributes = builder.globalAttributes.isEmpty()
                ? Optional.empty()
                : Optional.of(Collections.unmodifiableMap(new HashMap<>(builder.globalAttributes)));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The audience to enter into the Sequence.
     *
     * @return Selector
     */
    public Selector getAudience() {
        return audience;
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
     * An optional unique identifier for enrolling the same users in multiple
     * concurrent, independent instances of a Sequence.
     *
     * @return Optional entrance ID
     */
    public Optional<String> getEntranceId() {
        return entranceId;
    }

    /**
     * Optional key/value attributes used to personalize triggered messages.
     * Top-level keys must not start with the reserved prefix {@code ua_}.
     *
     * @return Optional map of global attributes
     */
    public Optional<Map<String, Object>> getGlobalAttributes() {
        return globalAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourneyTriggerPayload that = (JourneyTriggerPayload) o;
        return Objects.equal(audience, that.audience)
                && Objects.equal(triggeringIds, that.triggeringIds)
                && Objects.equal(entranceId, that.entranceId)
                && Objects.equal(globalAttributes, that.globalAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(audience, triggeringIds, entranceId, globalAttributes);
    }

    @Override
    public String toString() {
        return "JourneyTriggerPayload{"
                + "audience=" + audience
                + ", triggeringIds=" + triggeringIds
                + ", entranceId=" + entranceId
                + ", globalAttributes=" + globalAttributes
                + '}';
    }

    public static final class Builder {
        private Selector audience = null;
        private final List<String> triggeringIds = new ArrayList<>();
        private String entranceId = null;
        private final Map<String, Object> globalAttributes = new HashMap<>();

        private Builder() {}

        /**
         * Set the audience to enter into the Sequence. Required.
         *
         * @param audience Selector
         * @return Builder
         */
        public Builder setAudience(Selector audience) {
            this.audience = audience;
            return this;
        }

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
         * Set the entrance ID, used to track independent concurrent enrollments
         * of the same user in the same Sequence. Min length 1, max length 64.
         *
         * @param entranceId Entrance identifier
         * @return Builder
         */
        public Builder setEntranceId(String entranceId) {
            this.entranceId = entranceId;
            return this;
        }

        /**
         * Add a global attribute. Top-level keys must not begin with {@code ua_}.
         *
         * @param key   Attribute key
         * @param value Attribute value
         * @return Builder
         */
        public Builder addGlobalAttribute(String key, Object value) {
            this.globalAttributes.put(key, value);
            return this;
        }

        /**
         * Set all global attributes at once. Top-level keys must not begin with {@code ua_}.
         *
         * @param globalAttributes Map of attributes
         * @return Builder
         */
        public Builder setGlobalAttributes(Map<String, Object> globalAttributes) {
            this.globalAttributes.clear();
            this.globalAttributes.putAll(globalAttributes);
            return this;
        }

        public JourneyTriggerPayload build() {
            Preconditions.checkNotNull(audience, "'audience' must not be null");
            Preconditions.checkArgument(!triggeringIds.isEmpty(), "At least one triggering ID is required");
            Preconditions.checkArgument(triggeringIds.size() <= 10, "A maximum of 10 triggering IDs are allowed");
            if (entranceId != null) {
                Preconditions.checkArgument(!entranceId.isEmpty(), "'entranceId' must not be empty");
                Preconditions.checkArgument(entranceId.length() <= 64, "'entranceId' must not exceed 64 characters");
            }
            for (String key : globalAttributes.keySet()) {
                Preconditions.checkArgument(!key.startsWith("ua_"),
                        "Global attribute keys must not start with the reserved prefix 'ua_'");
            }
            return new JourneyTriggerPayload(this);
        }
    }
}
