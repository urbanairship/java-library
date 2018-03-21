package com.urbanairship.api.push.model.notification.open;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;

import java.util.Map;

public final class OpenPayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<String> alert;
    private final Optional<String> title;
    private final Optional<ImmutableMap<String,String>> extras;
    private final Optional<String> summary;
    private final Optional<String> mediaAttachment;
    private final Optional<Interactive> interactive;
    private final DeviceType deviceType;

    private OpenPayload(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
        this.title = Optional.fromNullable(builder.title);
        if (builder.extras.build().isEmpty()) {
            this.extras = Optional.absent();
        } else {
            this.extras = Optional.of(builder.extras.build());
        }
        this.summary = Optional.fromNullable(builder.summary);
        this.mediaAttachment = Optional.fromNullable(builder.mediaAttachment);
        this.interactive = Optional.fromNullable(builder.interactive);
        this.deviceType = builder.deviceType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * Optional, override the alert value provided at the top level, if any.
     *
     * @return Optional String alert.
     */
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Optional, a string representing the title of the notification.
     *
     * @return Optional String title.
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * A string to string map of additional values to deliver to the target.
     *
     * @return Optional ImmutableMap of Strings extras.
     */
    public Optional<ImmutableMap<String, String>> getExtras() {
        return extras;
    }

    /**
     * Optional, a string value for providing a content summary.
     *
     * @return Optional String summary
     */
    public Optional<String> getSummary() {
        return summary;
    }

    /**
     * Optional, a String representation of a URI for an image or video somewhere on the internet.
     *
     * @return Optional String mediaAttachment.
     */
    public Optional<String> getMediaAttachment() {
        return mediaAttachment;
    }

    /**
     * Optional. An Interactive object. Included button actions must be of type “add_tag”,
     * “remove tag”, app_defined” or “open” with subtype “url”.
     *
     * @return Optional Interactive interactive.
     */
    public Optional<Interactive> getInteractive() {
        return interactive;
    }

    @Override
    public String toString() {
        return "OpenPayload{" +
                "alert=" + alert +
                ", title=" + title +
                ", extras=" + extras +
                ", summary=" + summary +
                ", mediaAttachment=" + mediaAttachment +
                ", interactive=" + interactive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenPayload that = (OpenPayload) o;
        return Objects.equal(alert, that.alert) &&
                Objects.equal(title, that.title) &&
                Objects.equal(extras, that.extras) &&
                Objects.equal(summary, that.summary) &&
                Objects.equal(mediaAttachment, that.mediaAttachment) &&
                Objects.equal(interactive, that.interactive);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, title, extras, summary, mediaAttachment, interactive);
    }


    /**
     * OpenPayload Builder.
     */
    public static class Builder {
        private DeviceType deviceType = null;
        private String alert = null;
        private String title = null;
        private ImmutableMap.Builder<String, String> extras = ImmutableMap.builder();
        private String summary = null;
        private String mediaAttachment = null;
        private Interactive interactive = null;

        private Builder() { }

        /**
         * Optional, override the alert value provided at the top level, if any.
         *
         * @param alert String.
         * @return OpenPayload Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Optional, a string representing the title of the notification.
         *
         * @param title Optional String
         * @return OpenPayload Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * A string to string map of additional values to deliver to the target.
         *
         * @param entries Optional Map of Strings.
         * @return OpenPayload Builder
         */
        public Builder setExtras(Map<String, String> entries) {
            this.extras.putAll(entries);
            return this;
        }

        /**
         * Optional, a string value for providing a content summary.
         *
         * @param summary Optional String
         * @return OpenPayload Builder
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Optional, a String representation of a URI for an image or video somewhere on the internet.
         *
         * @param mediaAttachment String
         * @return OpenPayload Builder
         */
        public Builder setMediaAttachment(String mediaAttachment) {
            this.mediaAttachment = mediaAttachment;
            return this;
        }

        /**
         * Set the device type for the open channel payload.
         *
         * @param deviceType DeviceType
         * @return OpenPayload Builder
         */
        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        /**
         * Optional. An Interactive object. Included button actions must be of type “add_tag”,
         * “remove tag”, app_defined” or “open” with subtype “url”.
         *
         * @param interactive Interactive
         * @return OpenPayload Builder
         */
        public Builder setInteractive(Interactive interactive) {
            this.interactive = interactive;
            return this;
        }

        public OpenPayload build() {
            Preconditions.checkNotNull(deviceType, "DeviceType must be set.");
            return new OpenPayload(this);
        }
    }
}
