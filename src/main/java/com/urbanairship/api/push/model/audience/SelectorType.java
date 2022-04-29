/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.DeviceType;

public enum SelectorType {

    TAG("tag", SelectorCategory.VALUE),
    ALIAS("alias", SelectorCategory.VALUE),
    SEGMENT("segment", SelectorCategory.VALUE),
    NAMED_USER("named_user", SelectorCategory.VALUE),
    STATIC_LIST("static_list", SelectorCategory.VALUE),
    ATTRIBUTE("attribute", SelectorCategory.VALUE),
    SUBSCRIPTION_LIST("subscription_list", SelectorCategory.VALUE),

    DEVICE_TOKEN("device_token", SelectorCategory.VALUE, true, DeviceType.IOS),
    APID("apid", SelectorCategory.VALUE, true, DeviceType.ANDROID),
    WNS("wns", SelectorCategory.VALUE, true, DeviceType.WNS),
    AMAZON_CHANNEL("amazon_channel", SelectorCategory.VALUE, true, true, DeviceType.AMAZON),
    IOS_CHANNEL("ios_channel", SelectorCategory.VALUE, true, true, DeviceType.IOS),
    ANDROID_CHANNEL("android_channel", SelectorCategory.VALUE, true, true, DeviceType.ANDROID),
    OPEN_CHANNEL("open_channel", SelectorCategory.VALUE),
    CHANNEL("channel", SelectorCategory.VALUE),

    AND("and", SelectorCategory.COMPOUND),
    OR("or", SelectorCategory.COMPOUND),
    NOT("not", SelectorCategory.COMPOUND),

    LOCATION("location", SelectorCategory.LOCATION),

    SMS("sms_id", SelectorCategory.SMS),
    SMS_SENDER("sms_sender", SelectorCategory.VALUE),

    ALL("all", SelectorCategory.ATOMIC),
    TRIGGERED("triggered", SelectorCategory.ATOMIC);

    private final String identifier;
    private final SelectorCategory category;
    private final boolean isDeviceId;
    private final boolean isChannel;
    private final Optional<DeviceType> deviceType;

    SelectorType(String identifier, SelectorCategory category) {
        this.identifier = identifier;
        this.category = category;
        this.isDeviceId = false;
        this.isChannel = false;
        this.deviceType = Optional.absent();
    }

    SelectorType(String identifier, SelectorCategory category, boolean isDeviceId, DeviceType deviceType) {
        this.identifier = identifier;
        this.category = category;
        this.isDeviceId = isDeviceId;
        this.isChannel = false;
        this.deviceType = Optional.of(deviceType);
    }

    SelectorType(String identifier, SelectorCategory category, boolean isDeviceId, boolean isChannel, DeviceType deviceType) {
        this.identifier = identifier;
        this.category = category;
        this.isDeviceId = isDeviceId;
        this.isChannel = isChannel;
        this.deviceType = Optional.of(deviceType);
    }

    public String getIdentifier() {
        return identifier;
    }

    public SelectorCategory getCategory() {
        return category;
    }

    public boolean isDeviceId() {
        return isDeviceId;
    }

    public boolean isChannel() {
        return isChannel;
    }

    public Optional<DeviceType> getPlatform() {
        return deviceType;
    }

    public static SelectorType getSelectorTypeFromIdentifier(String identifier) {
        for (SelectorType operatorType : values()) {
            if (operatorType.identifier.equalsIgnoreCase(identifier)) {
                return operatorType;
            }
        }

        return null;
    }
}
