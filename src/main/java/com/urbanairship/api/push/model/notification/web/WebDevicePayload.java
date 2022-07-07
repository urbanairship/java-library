package com.urbanairship.api.push.model.notification.web;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.actions.Actions;

import java.util.Map;
import java.util.Optional;

/**
 * Represents the payload to be used for sending to web devices.
 */
public final class WebDevicePayload extends PushModelObject implements DevicePayloadOverride{

    private final Optional<String> alert;
    private final Optional<String> title;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<WebIcon> webIcon;
    private final Optional<Boolean> requireInteraction;
    private final Optional<Actions> actions;
    private final Optional<WebImage> webImage;
    private final Optional<PushExpiry> expiry;
    private final Optional<ImmutableList<Button>> buttons;
    private final Optional<WebTemplate> template;

    private WebDevicePayload(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.title = Optional.ofNullable(builder.title);
        this.webIcon = Optional.ofNullable(builder.webIcon);
        this.requireInteraction = Optional.ofNullable(builder.requireInteraction);
        this.actions = Optional.ofNullable(builder.actions);
        this.webImage = Optional.ofNullable(builder.webImage);
        this.expiry = Optional.ofNullable(builder.expiry);
        this.template = Optional.ofNullable(builder.webTemplate);

        if (builder.buttons.build().isEmpty()) {
            this.buttons = Optional.empty();
        } else {
            this.buttons = Optional.of(builder.buttons.build());
        }

        if (builder.extra.build().isEmpty()) {
            this.extra = Optional.empty();
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
     * @return DeviceType.WEB
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

    /**
     * Get the Actions.
     * Describes Actions to be performed by the SDK when a user interacts with the notification.
     *
     * @return Optional Actions
     */
    public Optional<Actions> getActions() {
        return actions;
    }

    /**
     * Get the WebImage object.
     * A object that describes an image to be used with the web alert.
     *
     * @return Optional WebImage
     */
    public Optional<WebImage> getWebImage() {
        return webImage;
    }

    /**
     * Get the PushExpiry
     * Delivery expiration, as either absolute ISO UTC timestamp, or number of seconds from now.
     *
     * @return Optional PushExpiry
     */
    public Optional<PushExpiry> getExpiry() {
        return expiry;
    }

    /**
     * Get the List of buttons.
     * Contains one or two buttons that perform actions for the web notification.
     * If you do not specify actions for a button, the button closes the notification without performing an action.
     *
     * @return Optional ImmutableList of Button objects.
     */
    public Optional<ImmutableList<Button>> getButtons() {
        return buttons;
    }

    /**
     * Get the template with web-specific message.
     *
     * @return Optional WebTemplate
     */
    public Optional<WebTemplate> getTemplate() {
        return template;
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
                Objects.equal(requireInteraction, payload.requireInteraction) &&
                Objects.equal(actions, payload.actions) &&
                Objects.equal(webImage, payload.webImage) &&
                Objects.equal(expiry, payload.expiry) &&
                Objects.equal(buttons, payload.buttons) &&
                Objects.equal(template, payload.template);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, title, extra, webIcon,
                requireInteraction, actions, webImage, expiry, buttons, template);
    }

    @Override
    public String toString() {
        return "WebDevicePayload{" +
                "alert=" + alert +
                ", title=" + title +
                ", extra=" + extra +
                ", webIcon=" + webIcon +
                ", requireInteraction=" + requireInteraction +
                ", actions=" + actions +
                ", webImage=" + webImage +
                ", expiry=" + expiry +
                ", buttons=" + buttons +
                ", template=" + template +
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
        private Actions actions = null;
        private WebImage webImage = null;
        private PushExpiry expiry = null;
        private ImmutableList.Builder<Button> buttons = ImmutableList.builder();
        private WebTemplate webTemplate = null;

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
         * Set the Actions.
         * Describes Actions to be performed by the SDK when a user interacts with the notification.
         *
         * @param actions Actions
         * @return Builder
         */
        public Builder setActions(Actions actions) {
            this.actions = actions;
            return this;
        }

        /**
         * Set the WebImage object.
         * A object that describes an image to be used with the web alert.
         *
         * @param webImage WebImage
         * @return Builder
         */
        public Builder setWebImage(WebImage webImage) {
            this.webImage = webImage;
            return this;
        }

        /**
         * Set the PushExpiry
         * Delivery expiration, as either absolute ISO UTC timestamp, or number of seconds from now.
         *
         * @param expiry PushExpiry
         * @return Builder
         */
        public Builder setExpiry(PushExpiry expiry) {
            this.expiry = expiry;
            return this;
        }

        /**
         * Add a Button.
         * Contains one or two buttons that perform actions for the web notification.
         * If you do not specify actions for a button, the button closes the notification without performing an action.
         *
         * @param button Button
         * @return Builder
         */
        public Builder addButton(Button button) {
            buttons.add(button);
            return this;
        }

        /**
         * Add iterable of Button objects.
         * Contains one or two buttons that perform actions for the web notification.
         * If you do not specify actions for a button, the button closes the notification without performing an action.
         *
         * @param buttons Buttons
         * @return Builder
         */
        public Builder addAllButtons(Iterable<? extends Button> buttons) {
            this.buttons.addAll(buttons);
            return this;
        }

        /**
         * Set a template with web-specific message.
         *
         * @param webTemplate WebTemplate
         * @return Builder
         */
        public Builder setTemplate(WebTemplate webTemplate) {
            this.webTemplate = webTemplate;
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
