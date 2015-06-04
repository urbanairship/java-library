/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.reports.Base64ByteArray;
import org.joda.time.DateTime;

import java.util.UUID;

public final class PerPushDetailResponse {

    private final String appKey;
    private final UUID pushID;
    private final Optional<DateTime> created;
    private final Optional<Base64ByteArray> pushBody;
    private final long richDeletions;
    private final long richResponses;
    private final long richSends;
    private final long sends;
    private final long directResponses;
    private final long influencedResponses;
    private final ImmutableMap<PlatformType, PerPushCounts> platforms;

    private PerPushDetailResponse(String appKey,
                                  UUID pushID,
                                  Optional<DateTime> created,
                                  Optional<Base64ByteArray> pushBody,
                                  long richDeletions,
                                  long richResponses,
                                  long richSends,
                                  long sends,
                                  long directResponses,
                                  long influencedResponses,
                                  ImmutableMap<PlatformType, PerPushCounts> platforms) {
        this.appKey = appKey;
        this.pushID = pushID;
        this.created = created;
        this.pushBody = pushBody;
        this.richDeletions = richDeletions;
        this.richResponses = richResponses;
        this.richSends = richSends;
        this.sends = sends;
        this.directResponses = directResponses;
        this.influencedResponses = influencedResponses;
        this.platforms = platforms;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAppKey() {
        return appKey;
    }

    public UUID getPushID() {
        return pushID;
    }

    public Optional<DateTime> getCreated() {
        return created;
    }

    public Optional<Base64ByteArray> getPushBody() {
        return pushBody;
    }

    public long getRichDeletions() {
        return richDeletions;
    }

    public long getRichResponses() {
        return richResponses;
    }

    public long getRichSends() {
        return richSends;
    }

    public long getSends() {
        return sends;
    }

    public long getDirectResponses() {
        return directResponses;
    }

    public long getInfluencedResponses() {
        return influencedResponses;
    }

    public ImmutableMap<PlatformType, PerPushCounts> getPlatforms() {
        return platforms;
    }

    @Override
    public String toString() {
        return "PerPushDetailResponse{" +
                "appKey='" + appKey + '\'' +
                ", pushID=" + pushID +
                ", created=" + created +
                ", pushBody=" + pushBody +
                ", richDeletions=" + richDeletions +
                ", richResponses=" + richResponses +
                ", richSends=" + richSends +
                ", sends=" + sends +
                ", directResponses=" + directResponses +
                ", influencedResponses=" + influencedResponses +
                ", platforms=" + platforms +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(appKey, pushID, created, pushBody, richDeletions, richResponses, richSends, sends, directResponses, influencedResponses, platforms);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PerPushDetailResponse other = (PerPushDetailResponse) obj;
        return Objects.equal(this.appKey, other.appKey) && Objects.equal(this.pushID, other.pushID) && Objects.equal(this.created, other.created) && Objects.equal(this.pushBody, other.pushBody) && Objects.equal(this.richDeletions, other.richDeletions) && Objects.equal(this.richResponses, other.richResponses) && Objects.equal(this.richSends, other.richSends) && Objects.equal(this.sends, other.sends) && Objects.equal(this.directResponses, other.directResponses) && Objects.equal(this.influencedResponses, other.influencedResponses) && Objects.equal(this.platforms, other.platforms);
    }

    public static class Builder {

        private String appKey;
        private UUID pushID;
        private Optional<DateTime> created;
        private Optional<Base64ByteArray> pushBody;
        private long richDeletions;
        private long richResponses;
        private long richSends;
        private long sends;
        private long directResponses;
        private long influencedResponses;
        private ImmutableMap.Builder<PlatformType, PerPushCounts> platforms = ImmutableMap.builder();

        private Builder() {
        }

        public Builder setAppKey(String value) {
            this.appKey = value;
            return this;
        }

        public Builder setPushID(UUID value) {
            this.pushID = value;
            return this;
        }

        public Builder setCreated(Optional<DateTime> value) {
            this.created = value;
            return this;
        }

        public Builder setPushBody(Optional<Base64ByteArray> value) {
            this.pushBody = value;
            return this;
        }

        public Builder setRichDeletions(long value) {
            this.richDeletions = value;
            return this;
        }

        public Builder setRichResponses(long value) {
            this.richResponses = value;
            return this;
        }

        public Builder setRichSends(long value) {
            this.richSends = value;
            return this;
        }

        public Builder setSends(long value) {
            this.sends = value;
            return this;
        }

        public Builder setDirectResponses(long value) {
            this.directResponses = value;
            return this;
        }

        public Builder setInfluencedResponses(long value) {
            this.influencedResponses = value;
            return this;
        }

        public Builder addPlatform(PlatformType type, PerPushCounts counts) {
            this.platforms.put(type, counts);
            return this;
        }

        public Builder addAllPlatforms(ImmutableMap<PlatformType, PerPushCounts> value) {
            this.platforms.putAll(value);
            return this;
        }

        public PerPushDetailResponse build() {
            return new PerPushDetailResponse(appKey, pushID, created, pushBody,
                    richDeletions, richResponses, richSends,
                    sends, directResponses, influencedResponses,
                    platforms.build());
        }
    }
}
