/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.open.OpenPayload;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;

public class Notifications {

    public static Notification alert(String text) {
        return Notification.newBuilder()
            .setAlert(text)
            .build();
    }

    public static Notification notification(String text, DevicePayloadOverride ... overrides) {
        Notification.Builder builder = Notification.newBuilder()
            .setAlert(text);
        for (DevicePayloadOverride override : overrides) {
            builder.addDeviceTypeOverride(override.getDeviceType(), override);
        }
        return builder.build();
    }

    public static Notification notification(DevicePayloadOverride ... overrides) {
        Notification.Builder builder = Notification.newBuilder();
        for (DevicePayloadOverride override : overrides) {
            builder.addDeviceTypeOverride(override.getDeviceType(), override);
        }
        return builder.build();
    }

    /* Simple alert deviceType overrides */

    public static DevicePayloadOverride alert(DeviceType deviceType, String text) {
        if (deviceType.isOpenPlatform()) {
            return openPayloadAlert(text, deviceType);
        }

        switch (deviceType.getIdentifier()) {
        case "ios":
            return iosAlert(text);
        case "android":
            return androidAlert(text);
        case "wns":
            return wnsAlert(text);
        case "amazon":
            return admAlert(text);
        case "web":
            return webAlert(text);

        default:
            throw unknownDeviceType(deviceType.getIdentifier());
        }
    }

    public static IOSDevicePayload iosAlert(String text) {
        return IOSDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static AndroidDevicePayload androidAlert(String text) {
        return AndroidDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static WNSDevicePayload wnsAlert(String text) {
        return WNSDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static ADMDevicePayload admAlert(String text) {
        return ADMDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static WebDevicePayload webAlert(String text) {
        return WebDevicePayload.newBuilder()
                .setAlert(text)
                .build();
    }

    public static OpenPayload openPayloadAlert(String text, DeviceType deviceType) {
        return OpenPayload.newBuilder()
                .setAlert(text)
                .setDeviceType(deviceType)
                .build();
    }

    /* Platform selector (device_types) */

    public static DeviceTypeData deviceTypes(String ... names) {
        DeviceTypeData.Builder deviceTypes = DeviceTypeData.newBuilder();
        for (String name : names) {
            Optional<DeviceType> deviceType = DeviceType.find(name);
            if (! deviceType.isPresent()) {
                throw unknownDeviceType(name);
            }
            deviceTypes.addDeviceType(deviceType.get());
        }
        return deviceTypes.build();
    }

    /* Rich Push */

    public static RichPushMessage richpush(String title, String body) {
        return RichPushMessage.newBuilder()
            .setTitle(title)
            .setBody(body)
            .build();
    }

    public static RichPushMessage richpush(String title, String body, String contentType) {
        return RichPushMessage.newBuilder()
            .setTitle(title)
            .setBody(body)
            .setContentType(contentType)
            .build();
    }

    /* Exceptions */

    public static IllegalArgumentException unknownDeviceType(String name) {
        throw new IllegalArgumentException(String.format("Unknown deviceType '%s'", name));
    }
}
