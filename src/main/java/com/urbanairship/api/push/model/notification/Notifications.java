/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.google.common.base.Optional;

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
            builder.addPlatformOverride(override.getPlatform(), override);
        }
        return builder.build();
    }

    public static Notification notification(DevicePayloadOverride ... overrides) {
        Notification.Builder builder = Notification.newBuilder();
        for (DevicePayloadOverride override : overrides) {
            builder.addPlatformOverride(override.getPlatform(), override);
        }
        return builder.build();
    }

    /* Simple alert platform overrides */

    public static DevicePayloadOverride alert(Platform platform, String text) {
        switch (platform) {
        case IOS:
            return iosAlert(text);
        case ANDROID:
            return androidAlert(text);
        case BLACKBERRY:
            return blackberryAlert(text);
        case WNS:
            return wnsAlert(text);
        case MPNS:
            return mpnsAlert(text);
        case ADM:
            return admAlert(text);
        default:
            throw unknownPlatform(platform.getIdentifier());
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

    public static BlackberryDevicePayload blackberryAlert(String text) {
        return BlackberryDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static WNSDevicePayload wnsAlert(String text) {
        return WNSDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static MPNSDevicePayload mpnsAlert(String text) {
        return MPNSDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    public static ADMDevicePayload admAlert(String text) {
        return ADMDevicePayload.newBuilder()
            .setAlert(text)
            .build();
    }

    /* Platform selector (device_types) */

    public static PlatformData platforms(String ... names) {
        PlatformData.Builder platforms = PlatformData.newBuilder();
        for (String name : names) {
            if (name.equalsIgnoreCase("all")) {
                return PlatformData.all();
            }
            Optional<Platform> platform = Platform.find(name);
            if (! platform.isPresent()) {
                throw unknownPlatform(name);
            }
            platforms.addPlatform(platform.get());
        }
        return platforms.build();
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

    public static IllegalArgumentException unknownPlatform(String name) {
        throw new IllegalArgumentException(String.format("Unknown platform '%s'", name));
    }
}
