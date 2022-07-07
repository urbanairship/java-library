/*
 * Copyright (c) 2013-2022.  Urban Airship and Contributors
 */

package com.urbanairship.api.tags.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Optional;

public class TagListView {
    private final String name;
    private final Optional<String> description;
    private final Optional<ImmutableMap<String, String>> extras;
    private final Optional<ImmutableMap<String, ImmutableList<String>>> addTags;
    private final Optional<ImmutableMap<String, ImmutableList<String>>> removeTags;
    private final Optional<ImmutableMap<String, ImmutableList<String>>> setTags;
    private final DateTime created;
    private final DateTime lastUpdated;
    private final Integer channelCount;
    private final Integer mutationSuccessCount;
    private final Integer mutationErrorCount;
    private final String errorPath;
    private final String status;

    private TagListView(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("extra") ImmutableMap<String, String> extras,
        @JsonProperty("add") ImmutableMap<String, ImmutableList<String>> addTags,
        @JsonProperty("remove") ImmutableMap<String, ImmutableList<String>> removeTags,
        @JsonProperty("set") ImmutableMap<String, ImmutableList<String>> setTags,
        @JsonProperty("created") DateTime created,
        @JsonProperty("last_updated") DateTime lastUpdated,
        @JsonProperty("channel_count") Integer channelCount,
        @JsonProperty("mutation_success_count") Integer mutationSuccessCount,
        @JsonProperty("mutation_error_count") Integer mutationErrorCount,
        @JsonProperty("error_path") String errorPath,
        @JsonProperty("status") String status

    ) {
        this.name = name;
        this.description = Optional.ofNullable(description);
        this.extras = Optional.ofNullable(extras);
        this.addTags = Optional.ofNullable(addTags);
        this.removeTags = Optional.ofNullable(removeTags);
        this.setTags = Optional.ofNullable(setTags);
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.channelCount = channelCount;
        this.mutationSuccessCount = mutationSuccessCount;
        this.mutationErrorCount = mutationErrorCount;
        this.errorPath = errorPath;
        this.status = status;
    }

    /**
     * New TagListView builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * Get the tag list name.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the tag list description.
     *
     * @return An Optional String
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get any extra values associated with the tag list.
     *
     * @return An Optional ImmutableMap of Strings to Strings
     */
    public Optional<ImmutableMap<String, String>> getExtras() {
        return extras;
    }

    /**
     * Get any add tags values associated with the tag list.
     *
     * @return An Optional ImmutableMap of Strings to Strings
     */
    public Optional<ImmutableMap<String, ImmutableList<String>>> getAddTags() {
        return addTags;
    }

    /**
     * Get any remove tags values associated with the tag list.
     *
     * @return An Optional ImmutableMap of Strings to Strings
     */
    public Optional<ImmutableMap<String, ImmutableList<String>>> getRemoveTags() {
        return removeTags;
    }

    /**
     * Get any set tags values associated with the tag list.
     *
     * @return An Optional ImmutableMap of Strings to Strings
     */
    public Optional<ImmutableMap<String, ImmutableList<String>>> getSetTags() {
        return setTags;
    }

    /**
     * Get the tag list creation date.
     *
     * @return DateTime
     */
    public DateTime getCreated() {
        return created;
    }

    /**
     * Get the date the tag list was last updated.
     *
     * @return An Optional DateTime
     */
    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Get the number of channels associated with the tag list.
     *
     * @return int channel count
     */
    public Integer getChannelCount() {
        return channelCount;
    }

    /**
     * Get the mutationSuccessCount of the tag list.
     *
     * @return int mutationSuccessCount
     */
    public Integer getmutationSuccessCount() {
        return mutationSuccessCount;
    }

    /**
     * Get the mutationErrorCount of the tag list.
     *
     * @return int mutationErrorCount
     */
    public Integer getMutationErrorCount() {
        return mutationErrorCount;
    }

    /**
     * Get the error path of the tag list.
     *
     * @return String
     */
    public String getErrorPath() {
        return errorPath;
    }

    /**
     * Get the status of the tag list.
     *
     * @return String, one of "ready", "processing", or "failure"
     */
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TagListView{" +
                "name=\'" + name + '\'' +
                ", description=" + description +
                ", extras=" + extras +
                ", addTags=" + addTags +
                ", removeTags=" + removeTags +
                ", setTags=" + setTags +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", channelCount=" + channelCount +
                ", mutationSuccessCount=" + mutationSuccessCount +
                ", mutationErrorCount=" + mutationErrorCount +
                ", errorPath=" + errorPath +
                ", status=" + status +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description, extras, addTags, removeTags, setTags, created, lastUpdated, channelCount, mutationSuccessCount, mutationErrorCount, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TagListView other = (TagListView) obj;
        return  Objects.equal(this.name, other.name) &&
                Objects.equal(this.description, other.description) &&
                Objects.equal(this.extras, other.extras) &&
                Objects.equal(this.addTags, other.addTags) &&
                Objects.equal(this.removeTags, other.removeTags) &&
                Objects.equal(this.setTags, other.setTags) &&
                Objects.equal(this.created, other.created) &&
                Objects.equal(this.lastUpdated, other.lastUpdated) &&
                Objects.equal(this.channelCount, other.channelCount) &&
                Objects.equal(this.mutationSuccessCount, other.mutationSuccessCount) &&
                Objects.equal(this.mutationErrorCount, other.mutationErrorCount) &&
                Objects.equal(this.errorPath, other.errorPath) &&
                Objects.equal(this.status, other.status);
    }


    public final static class Builder {
        private String name = null;
        private String description = null;
        private ImmutableMap.Builder<String, String> extras = ImmutableMap.builder();
        private ImmutableMap.Builder<String, ImmutableList<String>> addTags = ImmutableMap.builder();
        private ImmutableMap.Builder<String, ImmutableList<String>> removeTags = ImmutableMap.builder();
        private ImmutableMap.Builder<String, ImmutableList<String>> setTags = ImmutableMap.builder();
        private DateTime created = null;
        private DateTime lastUpdated = null;
        private Integer channelCount = null;
        private Integer mutationSuccessCount = null;
        private Integer mutationErrorCount = null;
        private String errorPath = null;
        private String status = null;

        private Builder() {
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
         * Set the mutationSuccessCount.
         *
         * @param mutationSuccessCount int
         * @return Builder
         */
        public Builder setMutationSuccessCount(int mutationSuccessCount) {
            this.mutationSuccessCount = mutationSuccessCount;
            return this;
        }

        /**
         * Set the mutationErrorCount.
         *
         * @param mutationErrorCount int
         * @return Builder
         */
        public Builder setMutationErrorCount(int mutationErrorCount) {
            this.mutationErrorCount = mutationErrorCount;
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
         * Add all key-value pairs.
         *
         * @param addTags ImmutableMap of arbitrary key-value pairs.
         * @return Builder
         */
        public Builder addAddTags(ImmutableMap<String, ImmutableList<String>> addTags) {
            if (!addTags.isEmpty()) {
                this.addTags.putAll(addTags);
            }
            return this;
        }

        /**
         * Add all key-value pairs.
         *
         * @param removeTags ImmutableMap of arbitrary key-value pairs.
         * @return Builder
         */
        public Builder addRemoveTags(ImmutableMap<String, ImmutableList<String>> removeTags) {
            if (!removeTags.isEmpty()) {
                this.removeTags.putAll(removeTags);
            }
            return this;
        }

        /**
         * Add all key-value pairs.
         *
         * @param setTags
         * @return Builder
         */
        public Builder addSetTags(ImmutableMap<String, ImmutableList<String>> setTags) {
            if (!setTags.isEmpty()) {
                this.setTags.putAll(setTags);
            }
            return this;
        }

        /**
         * Build the TagListView object
         * @return TagListView
         */
        public TagListView build() {
            return new TagListView(
                name, 
                description,
                extras.build(),
                addTags.build(),
                removeTags.build(),
                setTags.build(),
                created,
                lastUpdated,
                channelCount,
                mutationSuccessCount,
                mutationErrorCount,
                errorPath,
                status);
        }
    }
}