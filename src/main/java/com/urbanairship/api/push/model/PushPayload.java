/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.localization.Localization;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;

import java.util.List;

/**
 * Represents a Push payload for the Urban Airship API
 */
public final class PushPayload extends PushModelObject {

    private final Selector audience;
    private final Optional<Notification> notification;
    private final Optional<ImmutableList<Localization>> localizations;
    private final Optional<RichPushMessage> message;
    private final DeviceTypeData deviceTypes;
    private final Optional<PushOptions> pushOptions;
    private final Optional<InApp> inApp;
    private final Optional<Campaigns> campaigns;
    private final ImmutableMap<String, Object> globalAttributes;
    /**
     * PushPayload builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private PushPayload(Selector audience,
                        Optional<Notification> notification,
                        Optional<RichPushMessage> message,
                        DeviceTypeData deviceTypes,
                        Optional<PushOptions> pushOptions,
                        Optional<InApp> inApp,
                        Optional<Campaigns> campaigns,
                        ImmutableList<Localization> localizations,
                        ImmutableMap<String, Object> globalAttributes) {
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.deviceTypes = deviceTypes;
        this.pushOptions = pushOptions;
        this.inApp = inApp;
        this.campaigns = campaigns;
        this.globalAttributes = globalAttributes;

        if (localizations.isEmpty()) {
            this.localizations = Optional.absent();
        } else {
            this.localizations = Optional.of(localizations);
        }
    }

    /**
     * Get the audience
     * @return audience
     */
    public Selector getAudience() {
        return audience;
    }

    /**
     * Get the Notification. This is optional.
     * @return Optional Notification
     */
    public Optional<Notification> getNotification() {
        return notification;
    }

    /**
     * Get the rich push message. This is optional
     * @return Optional RichPushMessage
     */
    public Optional<RichPushMessage> getMessage() {
        return message;
    }

    /**
     * Get the deviceTypes
     * @return DeviceTypeData
     */
    public DeviceTypeData getDeviceTypes() {
        return deviceTypes;
    }

    /**
     * Boolean indicating whether audience is SelectorType.ALL
     * @return audience is all
     */
    public boolean isBroadcast() {
        return audience.getType().equals(SelectorType.ALL);
    }

    /**
     * Get the optional in app message.
     *
     * @return An optional InApp message object.
     */
    public Optional<InApp> getInApp() {
        return inApp;
    }

    public Optional<PushOptions> getPushOptions() {
        return pushOptions;
    }

    /**
     * Get the optional Campaign.
     *
     * @return An optional Campaign object.
     */
    public Optional<Campaigns> getCampaigns() {
        return campaigns;
    }

    /**
     * Get the localizations.
     * @return An Optional immutable list of Localizations in the push payload.
     */
    public Optional<ImmutableList<Localization>> getLocalizations() {
        return localizations;
    }

    /**
     * Get the global attributes.
     * @return An Optional immutable map of global attributes in the push payload.
     */
    public ImmutableMap<String, Object> getGlobalAttributes() {
        return globalAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushPayload that = (PushPayload) o;

        if (audience != null ? !audience.equals(that.audience) : that.audience != null) {
            return false;
        }
        if (notification != null ? !notification.equals(that.notification) : that.notification != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        if (deviceTypes != null ? !deviceTypes.equals(that.deviceTypes) : that.deviceTypes != null) {
            return false;
        }
        if (pushOptions != null ? !pushOptions.equals(that.pushOptions) : that.pushOptions != null) {
            return false;
        }
        if (inApp != null ? !inApp.equals(that.inApp) : that.inApp != null) {
            return false;
        }
        if (campaigns != null ? !campaigns.equals(that.campaigns) : that.campaigns != null) {
            return false;
        }
        if (globalAttributes != null ? !globalAttributes.equals(that.globalAttributes) : that.globalAttributes != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (audience != null ? audience.hashCode() : 0);
        result = 31 * result + (notification != null ? notification.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (deviceTypes != null ? deviceTypes.hashCode() : 0);
        result = 31 * result + (pushOptions != null ? pushOptions.hashCode() : 0);
        result = 31 * result + (inApp != null ? inApp.hashCode() : 0);
        result = 31 * result + (campaigns != null ? campaigns.hashCode() : 0);
        result = 31 * result + (globalAttributes != null ? globalAttributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
                "audience=" + audience +
                ", notification=" + notification +
                ", message=" + message +
                ", deviceTypes=" + deviceTypes +
                ", pushOptions=" + pushOptions +
                ", inApp=" + inApp +
                ", campaigns=" + campaigns +
                ", globalAttributes=" + globalAttributes +
                '}';
    }

    public static class Builder {
        private DeviceTypeData deviceTypes = null;
        private Selector audience = null;
        private Notification notification = null;
        private RichPushMessage message = null;
        private PushOptions pushOptions = null;
        private InApp inApp = null;
        private Campaigns campaigns = null;
        private ImmutableList.Builder<Localization> localizationsBuilder = ImmutableList.builder();
        private ImmutableMap.Builder<String, Object> globalAttributesBuilder = ImmutableMap.builder();

        private Builder() { }

        /**
         * Set the Audience.
         * @param value audience Selector
         * @return Builder
         */
        public Builder setAudience(Selector value) {
            this.audience = value;
            return this;
        }

        /**
         * Set the Notification.
         * @param notification Notification
         * @return Builder
         */
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Set the rich push message.
         * @param message RichPushMessage
         * @return Builder
         */
        public Builder setMessage(RichPushMessage message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Device Type data.
         * @param deviceTypes DeviceTypeData
         * @return Builder
         */
        public Builder setDeviceTypes(DeviceTypeData deviceTypes) {
            this.deviceTypes = deviceTypes;
            return this;
        }

        /**
         * Set the push options.
         * @param pushOptions PushOptions
         * @return Builder
         */
        public Builder setPushOptions(PushOptions pushOptions) {
            this.pushOptions = pushOptions;
            return this;
        }

        /**
         * Set the in-app message.
         * @param inApp An InApp message object.
         * @return Builder
         */
        public Builder setInApp(InApp inApp) {
            this.inApp = inApp;
            return this;
        }

        /**
         * Set the campaign.
         * @param campaigns A campaign object.
         * @return Builder
         */
        public Builder setCampaigns(Campaigns campaigns) {
            this.campaigns = campaigns;
            return this;
        }

        /**
         * Add a Localization.
         * @param localization Localization
         * @return Builder
         */
        public Builder addLocalization(Localization localization) {
            localizationsBuilder.add(localization);
            return this;
        }

        /**
         * Add a global attribute.
         * @param globalAttributes ImmutableMap
         * @return Builder
         */
        public Builder addGlobalAttributes(String k, Object o) {
            this.globalAttributesBuilder.put(k, o);
            return this;
        }

        /**
         * Build a PushPayload object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. At least one of notification, message, or inApp must be set.
         * 2. Audience must be set.
         * 3. DeviceTypes (device types) must be set.
         * </pre>
         *
         * @throws IllegalArgumentException if an illegal argument is used
         * @throws NullPointerException if required variables are not initialized
         * @return PushPayload
         */
        public PushPayload build() {
            ImmutableList<Localization> localizations = localizationsBuilder.build();
            ImmutableMap<String, Object> globalAttributes = globalAttributesBuilder.build();
            Preconditions.checkArgument(!(notification == null && message == null && inApp == null),
                    "At least one of 'notification', 'message', or 'inApp' must be set.");
            Preconditions.checkNotNull(audience, "'audience' must be set");
            Preconditions.checkNotNull(deviceTypes, "'device_types' must be set");

            return new PushPayload(
                    audience,
                    Optional.fromNullable(notification),
                    Optional.fromNullable(message),
                    deviceTypes,
                    Optional.fromNullable(pushOptions),
                    Optional.fromNullable(inApp),
                    Optional.fromNullable(campaigns),
                    localizations,
                    globalAttributes
            );
        }
    }
}
