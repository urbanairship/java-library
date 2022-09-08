/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.model;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.model.ErrorDetails;
import org.joda.time.DateTime;

import java.util.Objects;
import java.util.Optional;

public class StaticListView {
    private final Optional<Boolean> ok;
    private final String name;
    private final Optional<String> description;
    private final Optional<ImmutableMap<String, String>> extras;
    private final DateTime created;
    private final DateTime lastUpdated;
    private final Integer channelCount;
    private final String status;
    private final String error;
    private final ErrorDetails errorDetails;

    private StaticListView(
            Boolean ok,
            String name,
            String description,
            ImmutableMap.Builder<String, String> extras,
            DateTime created,
            DateTime lastUpdated,
            Integer channelCount,
            String status,
            String error,
            ErrorDetails errorDetails) {
        this.ok = Optional.ofNullable(ok);
        this.name = name;
        this.description = Optional.ofNullable(description);
        this.extras = Optional.ofNullable(extras.build());
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.channelCount = channelCount;
        this.status = status;
        this.error = error;
        this.errorDetails = errorDetails;
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

    public String getError() {
        return error;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "StaticListView{" +
                "ok=" + ok +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", extras=" + extras +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", channelCount=" + channelCount +
                ", status='" + status + '\'' +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaticListView that = (StaticListView) o;
        return Objects.equals(ok, that.ok) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(extras, that.extras) &&
                Objects.equals(created, that.created) &&
                Objects.equals(lastUpdated, that.lastUpdated) &&
                Objects.equals(channelCount, that.channelCount) &&
                Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ok, name, description, extras, created, lastUpdated, channelCount, status, error, errorDetails);
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
        private String error = null;
        private ErrorDetails errorDetails = null;

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

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        /**
         * Build the StaticListView object
         * @return StaticListView
         */
        public StaticListView build() {
            return new StaticListView(ok, name, description, extras, created, lastUpdated, channelCount, status, error, errorDetails);
        }
    }
}