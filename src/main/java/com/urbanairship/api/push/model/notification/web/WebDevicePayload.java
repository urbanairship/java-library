package com.urbanairship.api.push.model.notification.web;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

import java.util.Map;

/**
 * Represents the payload to be used for sending to web devices.
 */
public final class WebDevicePayload extends PushModelObject implements DevicePayloadOverride{

    private final Optional<String> alert;
    private final Optional<String> title;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<WebIcon> webIcon;
    private final Optional<Boolean> requireInteraction;

    private WebDevicePayload(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
        this.title = Optional.fromNullable(builder.title);
        this.webIcon = Optional.fromNullable(builder.webIcon);
        this.requireInteraction = Optional.fromNullable(builder.requireInteraction);

        if (builder.extra.build().isEmpty()) {
            this.extra = Optional.absent();
        } else {
            this.extra = Optional.of(builder.extra.build());
        }
    }

    /**
     * New WebDevicePayload Builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the device type.
     *
     * @return DeviceStats.WEB
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.WEB;
    }

    /**
     * Get the alert.
     *
     * @return Optional String alert
     */
    @Override
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the title.
     *
     * @return Optional String title
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Get an extra mapping of key-value pairs.
     *
     * @return Optional ImmutableMap of Strings
     */
    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    /**
     * Get the web icon that describes an icon to be used with the web alert.
     *
     * @return Optional WebIcon object
     */
    public Optional<WebIcon> getWebIcon() {
        return webIcon;
    }

    /**
     * Get the Require Interaction flag.
     *
     * @return Optional Boolean require interaction flag.
     */
    public Optional<Boolean> getRequireInteraction() {
        return requireInteraction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebDevicePayload payload = (WebDevicePayload) o;

        return Objects.equal(alert, payload.alert) &&
                Objects.equal(title, payload.title) &&
                Objects.equal(extra, payload.extra) &&
                Objects.equal(webIcon, payload.webIcon)&&
                Objects.equal(requireInteraction, payload.requireInteraction);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, title, extra, webIcon,
                requireInteraction);
    }

    @Override
    public String toString() {
        return "WebDevicePayload{" +
                "alert=" + alert +
                ", title=" + title +
                ", extra=" + extra +
                ", webIcon=" + webIcon +
                ", requireInteraction=" + requireInteraction +
                '}';
    }

    /**
     * WebDevicePayload Builder.
     */
    public static class Builder {
        private String alert = null;
        private String title = null;
        private ImmutableMap.Builder<String, String> extra = ImmutableMap.builder();
        private WebIcon webIcon = null;
        private Boolean requireInteraction = null;

        private Builder() { }

        /**
         * Set the alert string.
         *
         * @param alert String
         * @return WebDevicePayload Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the title string.
         *
         * @param title String
         * @return WebDevicePayload Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Add an extra key-value pair.
         *
         * @param key String
         * @param value String
         * @return WebDevicePayload Builder
         */
        public Builder addExtraEntry(String key, String value) {
            this.extra.put(key, value);
            return this;
        }

        /**
         * Add a Map of key-value pairs.
         *
         * @param entries A Map of Strings
         * @return WebDevicePayload Builder
         */
        public Builder addAllExtraEntries(Map<String, String> entries) {
            this.extra.putAll(entries);
            return this;
        }

        /**
         * Set the webIcon payload.
         *
         * @param webIcon a {@link WebIcon}
         * @return WebDevicePayload Builder
         */
        public Builder setWebIcon(WebIcon webIcon) {
            this.webIcon = webIcon;
            return this;
        }

        /**
         * Set the Require Interaction flag.
         *
         * @param value Boolean
         * @return Builder
         */
        public Builder setRequireInteraction(boolean value){
            this.requireInteraction = value;
            return this;
        }

        /**
         * Build the WebDevicePayload object.
         *
         * @return WebDevicePayload
         */
        public WebDevicePayload build() {
            return new WebDevicePayload(this);
        }
    }
}
