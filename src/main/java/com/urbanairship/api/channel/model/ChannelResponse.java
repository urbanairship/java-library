/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

/**
 * Channel listing response object
 */
public class ChannelResponse {
    private final boolean ok;
    private final Optional<String> nextPage;
    private final Optional<ChannelView> channelObject;
    private final Optional<ImmutableList<ChannelView>> channelObjects;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private ChannelResponse() {
        this(false, null, null, null, null, null);
    }

    private ChannelResponse(boolean ok, String nextPage, Optional<ChannelView> channelObject, Optional<ImmutableList<ChannelView>> channelObjects, String error, ErrorDetails errorDetails) {
        this.ok = ok;
        this.nextPage = Optional.ofNullable(nextPage);
        this.channelObject = channelObject;
        this.channelObjects = channelObjects;
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
    }

    /**
     * New ChannelResponse builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the next page if present for a channel listing request
     *
     * @return An Optional String
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Get the channel object if present for a single channel lookup request
     *
     * @return An Optional ChannelView
     */
    public Optional<ChannelView> getChannelView() {
        return channelObject;
    }

    /**
     * Get a list of channel objects if present for a channel listing request
     *
     * @return An Optional ImmutableList of ChannelView objects
     */
    public Optional<ImmutableList<ChannelView>> getChannelObjects() {
        return channelObjects;
    }

    /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "ChannelResponse{" +
            "ok=" + ok +
            ", nextPage='" + nextPage + '\'' +
            ", channelObject=" + channelObject +
            ", channelObjects=" + channelObjects +
            ", error=" + error +
            ", errorDetails=" + errorDetails +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, nextPage, channelObject, channelObjects, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChannelResponse other = (ChannelResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.channelObject, other.channelObject) && Objects.equal(this.channelObjects, other.channelObjects)
        && Objects.equal(this.error, other.error) && Objects.equal(this.errorDetails, other.errorDetails);
    }

    public static class Builder {

        private boolean ok;
        private String nextPage = null;
        private ChannelView channelObject = null;
        private ImmutableList.Builder<ChannelView> channelObjects = ImmutableList.builder();
        private String error = null;
        private ErrorDetails errorDetails = null;

        private Builder() {
        }

        /**
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the next page
         *
         * @param value String
         * @return Builder
         */
        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        /**
         * Set the channel object for single channel lookup
         *
         * @param value ChannelView
         * @return Builder
         */
        public Builder setChannelObject(ChannelView value) {
            this.channelObject = value;
            return this;
        }

        /**
         * Add a channel object for channel listings
         *
         * @param value ChannelView
         * @return Builder
         */
        public Builder addChannel(ChannelView value) {
            this.channelObjects.add(value);
            return this;
        }

        /**
         * Add all the channel objects for channel listings
         *
         * @param value Iterable of ChannelView objects
         * @return Builder
         */
        public Builder addAllChannels(Iterable<? extends ChannelView> value) {
            this.channelObjects.addAll(value);
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        /**
         * Build the ChannelResponse object
         *
         * <pre>
         * 1. If channelObject is set, nextPage and channelObjects must be null.
         * </pre>
         *
         * @return ChannelResponse
         */
        public ChannelResponse build() {
            if (channelObject != null) {
                Preconditions.checkArgument(nextPage == null && channelObjects.build().isEmpty());
            }

            return new ChannelResponse(ok, nextPage,Optional.ofNullable(channelObject), Optional.ofNullable(channelObjects.build()), error, errorDetails);
        }
    }
}