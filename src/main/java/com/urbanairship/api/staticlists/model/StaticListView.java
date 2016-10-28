/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

public class StaticListView {
    private final Optional<Boolean> ok;
    private final String name;
    private final Optional<String> description;
    private final Optional<ImmutableMap<String, String>> extras;
    private final DateTime created;
    private final DateTime lastUpdated;
    private final Integer channelCount;
    private final String status;

    private StaticListView(Builder builder) {
        this.ok = Optional.fromNullable(builder.ok);
        this.name = builder.name;
        this.description = Optional.fromNullable(builder.description);
        this.extras = Optional.fromNullable(builder.extras.build());
        this.created = builder.created;
        this.lastUpdated = builder.lastUpdated;
        this.channelCount = builder.channelCount;
        this.status = builder.status;
    }

    /**
     * New ChannelView builder.
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
     * Get the static list name.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the static list description.
     *
     * @return An Optional String
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get any extra values associated with the static list.
     *
     * @return An Optional ImmutableMap of Strings to Strings
     */
    public Optional<ImmutableMap<String, String>> getExtras() {
        return extras;
    }

    /**
     * Get the static list creation date.
     *
     * @return DateTime
     */
    public DateTime getCreated() {
        return created;
    }

    /**
     * Get the date the static list was last updated.
     *
     * @return An Optional DateTime
     */
    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Get the number of channels associated with the static list.
     *
     * @return int channel count
     */
    public Integer getChannelCount() {
        return channelCount;
    }

    /**
     * Get the status of the static list.
     *
     * @return String, one of "ready", "processing", or "failure"
     */
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "StaticListView{" +
                "ok=" + ok +
                ", name=\'" + name + '\'' +
                ", description=" + description +
                ", extras=" + extras +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", channelCount=" + channelCount +
                ", status=" + status +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, name, description, extras, created, lastUpdated, channelCount, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final StaticListView other = (StaticListView) obj;
        return Objects.equal(this.ok, other.ok) &&
                Objects.equal(this.name, other.name) &&
                Objects.equal(this.description, other.description) &&
                Objects.equal(this.extras, other.extras) &&
                Objects.equal(this.created, other.created) &&
                Objects.equal(this.lastUpdated, other.lastUpdated) &&
                Objects.equal(this.channelCount, other.channelCount) &&
                Objects.equal(this.status, other.status);
    }


    public final static class Builder {
        private Boolean ok = null;
        private String name = null;
        private String description = null;
        private ImmutableMap.Builder<String, String> extras = ImmutableMap.builder();
        private DateTime created = null;
        private DateTime lastUpdated = null;
        private Integer channelCount = null;
        private String status = null;

        private Builder() {
        }

        /**
         * Set ok.
         *
         * @param ok
         * @return Builder
         */
        public Builder setOk(Boolean ok) {
            this.ok = ok;
            return this;
        }

        /**
         * Set the name.
         *
         * @param name
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the description.
         *
         * @param description
         * @return Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Set the creation date
         *
         * @param created
         * @return Builder
         */
        public Builder setCreated(DateTime created) {
            this.created = created;
            return this;
        }

        /**
         * Set the last updated date.
         *
         * @param lastUpdated
         * @return Builder
         */
        public Builder setLastUpdated(DateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        /**
         * Set the channel count.
         *
         * @param channelCount
         * @return Builder
         */
        public Builder setChannelCount(int channelCount) {
            this.channelCount = channelCount;
            return this;
        }

        /**
         * Set the status.
         *
         * @param status
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
         * Build the StaticListView object
         * @return StaticListView
         */
        public StaticListView build() {
            Preconditions.checkNotNull(name);
            Preconditions.checkNotNull(created);
            Preconditions.checkNotNull(lastUpdated);
            Preconditions.checkNotNull(channelCount);
            Preconditions.checkNotNull(status);

            return new StaticListView(this);
        }
    }
}