/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Optional;

public class AttributeListsView {
    private final Optional<Boolean> ok;
    private final String name;
    private final Optional<String> description;
    private final Optional<ImmutableMap<String, String>> extras;
    private final DateTime created;
    private final DateTime lastUpdated;
    private final Integer channelCount;
    private final String errorPath;
    private final String status;

    private AttributeListsView(Builder builder) {
        this.ok = Optional.ofNullable(builder.ok);
        this.name = builder.name;
        this.description = Optional.ofNullable(builder.description);
        this.extras = Optional.of(builder.extras.build());
        this.created = builder.created;
        this.lastUpdated = builder.lastUpdated;
        this.channelCount = builder.channelCount;
        this.errorPath = builder.errorPath;
        this.status = builder.status;
    }

    /**
     * New AttributeListsView builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the value for ok.
     *
     * @return An Optional Boolean ok
     */
    public Optional<Boolean> getOk() {
        return ok;
    }

    /**
     * Get the attribute list name.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the attribute list description.
     *
     * @return An Optional String
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get any extra values associated with the attribute list.
     *
     * @return An Optional ImmutableMap of Strings to Strings
     */
    public Optional<ImmutableMap<String, String>> getExtras() {
        return extras;
    }

    /**
     * Get the attribute list creation date.
     *
     * @return DateTime
     */
    public DateTime getCreated() {
        return created;
    }

    /**
     * Get the date the attribute list was last updated.
     *
     * @return An Optional DateTime
     */
    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Get the number of channels associated with the attribute list.
     *
     * @return int channel count
     */
    public Integer getChannelCount() {
        return channelCount;
    }

    /**
     * Get the errorPath of the attribute list.
     *
     * @return String
     */
    public String getErrorPath() {
        return errorPath;
    }


    /**
     * Get the status of the attribute list.
     *
     * @return String, one of "ready", "processing", or "failure"
     */
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AttributeListsView{" +
                "ok=" + ok +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", extras=" + extras +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", channelCount=" + channelCount +
                ", errorPath=" + errorPath +
                ", status=" + status +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, name, description, extras, created, lastUpdated, channelCount, errorPath, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AttributeListsView other = (AttributeListsView) obj;
        return Objects.equal(this.ok, other.ok) &&
                Objects.equal(this.name, other.name) &&
                Objects.equal(this.description, other.description) &&
                Objects.equal(this.extras, other.extras) &&
                Objects.equal(this.created, other.created) &&
                Objects.equal(this.lastUpdated, other.lastUpdated) &&
                Objects.equal(this.channelCount, other.channelCount) &&
                Objects.equal(this.errorPath, other.errorPath) &
                Objects.equal(this.status, other.status);
    }


    public final static class Builder {
        private Boolean ok = null;
        private String name = null;
        private String description = null;
        private final ImmutableMap.Builder<String, String> extras = ImmutableMap.builder();
        private DateTime created = null;
        private DateTime lastUpdated = null;
        private Integer channelCount = null;
        private String errorPath = null;
        private String status = null;

        private Builder() {
        }

        /**
         * Set ok.
         *
         * @param ok Boolean
         * @return Builder
         */
        public Builder setOk(Boolean ok) {
            this.ok = ok;
            return this;
        }

        /**
         * Set the name.
         *
         * @param name String
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the description.
         *
         * @param description String
         * @return Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Set the creation date
         *
         * @param created DateTime
         * @return Builder
         */
        public Builder setCreated(DateTime created) {
            this.created = created;
            return this;
        }

        /**
         * Set the last updated date.
         *
         * @param lastUpdated DateTime
         * @return Builder
         */
        public Builder setLastUpdated(DateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        /**
         * Set the channel count.
         *
         * @param channelCount int
         * @return Builder
         */
        public Builder setChannelCount(int channelCount) {
            this.channelCount = channelCount;
            return this;
        }

        /**
         * Set the errorPath.
         *
         * @param errorPath String
         * @return Builder
         */
        public Builder setErrorPath(String errorPath) {
            this.errorPath = errorPath;
            return this;
        }

        /**
         * Set the status.
         *
         * @param status String
         * @return Builder
         */
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        /**
         * Add an extra key-value pair.
         *
         * @param key A key of an arbitrary key-value pair.
         * @param val A val of an arbitrary key-value pair.
         * @return Builder
         */
        public Builder addExtra(String key, String val) {
            this.extras.put(key, val);
            return this;
        }

        /**
         * Add all key-value pairs.
         *
         * @param extras ImmutableMap of arbitrary key-value pairs.
         * @return Builder
         */
        public Builder addAllExtras(ImmutableMap<String, String> extras) {
            if (!extras.isEmpty()) {
                this.extras.putAll(extras);
            }
            return this;
        }

        /**
         * Build the AttributeListsView object
         * @return AttributeListsView
         */
        public AttributeListsView build() {
            Preconditions.checkNotNull(name);
            Preconditions.checkNotNull(created);
            Preconditions.checkNotNull(lastUpdated);
            Preconditions.checkNotNull(channelCount);
            Preconditions.checkNotNull(errorPath);
            Preconditions.checkNotNull(status);

            return new AttributeListsView(this);
        }
    }
}